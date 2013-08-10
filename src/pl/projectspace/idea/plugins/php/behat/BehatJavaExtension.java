package pl.projectspace.idea.plugins.php.behat;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.PathUtil;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.Method;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.cucumber.CucumberJvmExtensionPoint;
import org.jetbrains.plugins.cucumber.StepDefinitionCreator;
import org.jetbrains.plugins.cucumber.psi.GherkinFile;
import org.jetbrains.plugins.cucumber.psi.GherkinRecursiveElementVisitor;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import org.jetbrains.plugins.cucumber.steps.AbstractStepDefinition;
import org.jetbrains.plugins.cucumber.steps.CucumberStepsIndex;
import pl.projectspace.idea.plugins.php.behat.code.generator.BehatStepCreator;
import pl.projectspace.idea.plugins.php.behat.psi.element.BehatContextClass;
import pl.projectspace.idea.plugins.php.behat.psi.element.BehatStepImplementation;
import pl.projectspace.idea.plugins.php.behat.service.ContextLocator;

import java.util.*;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatJavaExtension implements CucumberJvmExtensionPoint {

    public static final String CUCUMBER_RUNTIME_JAVA_STEP_DEF_ANNOTATION = "cucumber.runtime.java.StepDefAnnotation";

    @Override
    public boolean isStepLikeFile(@NotNull PsiElement psiElement, @NotNull PsiElement psiElement2) {
        return psiElement instanceof PhpFile && ((PhpFile)psiElement).getName().endsWith(".php");
    }

    @Override
    public boolean isWritableStepLikeFile(@NotNull PsiElement psiElement, @NotNull PsiElement psiElement2) {
        return isStepLikeFile(psiElement, psiElement2);
    }

    @NotNull
    @Override
    public List<AbstractStepDefinition> getStepDefinitions(@NotNull PsiFile psiFile) {
        System.out.println(psiFile.getName());
        return Collections.emptyList();
    }

    @NotNull
    @Override
    public FileType getStepFileType() {
        return PhpFileType.INSTANCE;
    }

    @NotNull
    @Override
    public StepDefinitionCreator getStepDefinitionCreator() {
        return new BehatStepCreator();
    }

    @NotNull
    @Override
    public String getDefaultStepFileName() {
        return "FeatureContext";
    }

    @Override
    public void collectAllStepDefsProviders(@NotNull List<VirtualFile> providers, @NotNull Project project) {
        final Module[] modules = ModuleManager.getInstance(project).getModules();
            for (Module module : modules) {
                System.out.println(ModuleType.get(module).getName());
//            if (ModuleType.get(module) instanceof JavaModuleType) {
//                final VirtualFile[] roots = ModuleRootManager.getInstance(module).getContentRoots();
//                ContainerUtil.addAll(providers, roots);
//            }
        }
    }

    @Override
    public void loadStepDefinitionRootsFromLibraries(@NotNull Module module, List<PsiDirectory> psiDirectories, @NotNull Set<String> strings) {
        // @todo
    }

    @Override
    public List<PsiElement> resolveStep(@NotNull PsiElement element) {
        final CucumberStepsIndex index = CucumberStepsIndex.getInstance(element.getProject());

        if (element instanceof GherkinStep) {
            final GherkinStep step = (GherkinStep)element;
            final List<PsiElement> result = new ArrayList<PsiElement>();
            final Set<String> substitutedNameList = step.getSubstitutedNameList();
            if (substitutedNameList.size() > 0) {
                for (String s : substitutedNameList) {
                    final AbstractStepDefinition definition = index.findStepDefinition(element.getContainingFile(), s);
                    if (definition != null) {
                        result.add(definition.getElement());
                    }
                }
                return result;
            }
        }

        return Collections.emptyList();
    }

    @Override
    public void findRelatedStepDefsRoots(Module module, PsiFile featureFile, List<PsiDirectory> newStepDefinitionsRoots, Set<String> processedStepDirectories) {
        final ContentEntry[] contentEntries = ModuleRootManager.getInstance(module).getContentEntries();

        for (final ContentEntry contentEntry : contentEntries) {
            final SourceFolder[] sourceFolders = contentEntry.getSourceFolders();
            for (SourceFolder sf : sourceFolders) {
                // ToDo: check if inside test folder
                VirtualFile sfDirectory = sf.getFile();
                if (sfDirectory != null && sfDirectory.isDirectory()) {
                    PsiDirectory sourceRoot = PsiDirectoryFactory.getInstance(module.getProject()).createDirectory(sfDirectory);
                    if (!processedStepDirectories.contains(sourceRoot.getVirtualFile().getPath())) {
                        newStepDefinitionsRoots.add(sourceRoot);
                    }
                }
            }
        }
    }

    @NotNull
    @Override
    public Collection<String> getGlues(@NotNull GherkinFile gherkinFile, Set<String> gluesFromOtherFiles) {
        final Set<String> glues = ContainerUtil.newHashSet();
        if (gluesFromOtherFiles != null) {
            glues.addAll(gluesFromOtherFiles);
        }

        gherkinFile.accept(new GherkinRecursiveElementVisitor() {
            @Override
            public void visitStep(GherkinStep step) {
                final String glue = getGlue(step);
                if (glue != null) {
                    glues.add(glue);
                }
            }
        });

        return glues;
    }

    @Nullable
    public String getGlue(@NotNull GherkinStep step) {
        for (PsiReference ref : step.getReferences()) {
            PsiElement refElement = ref.resolve();
            if (refElement != null && refElement instanceof Method) {
                PhpFile phpFile = (PhpFile)refElement.getContainingFile();
                VirtualFile vfile = phpFile.getVirtualFile();
                if (vfile != null) {
                    VirtualFile parentDir = vfile.getParent();
                    return PathUtil.getLocalPath(parentDir);
                }
            }
        }
        return null;
    }

    public List<GherkinStep> loadStepsFor(@Nullable PsiFile featureFile, @NotNull Module module) {
        final GlobalSearchScope dependenciesScope = module.getModuleWithDependenciesAndLibrariesScope(true);

        final List<GherkinStep> result = new ArrayList<GherkinStep>();

        Collection<BehatContextClass> contextClasses = ServiceManager.getService(featureFile.getProject(), ContextLocator.class).getContextClasses();

        for (BehatContextClass behatContext : contextClasses) {
            for(BehatStepImplementation step : (behatContext).getStepImplementations()) {
                result.add(step.getDefinition());
            }
        }

        return result;
    }

}