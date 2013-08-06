package pl.projectspace.idea.plugins.php.behat.service;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.findUsages.PhpFindUsagesHandlerFactory;
import com.jetbrains.php.lang.findUsages.PhpFindUsagesProvider;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpUse;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextLocator {

    public static final String BASE_CONTEXT_CLASS = "\\Behat\\Behat\\Context\\BehatContext";

    public Collection<PhpClass> getContextClasses(Project project){
        final PhpIndex index = PhpIndex.getInstance(project);

        return index.getAllSubclasses(BASE_CONTEXT_CLASS);
    }

    public PhpClass getMainContextFor(PhpClass phpClass) {
        PhpClass base = getBaseContextClass(phpClass.getProject());

        if (base == null) {
            return null;
        }

        Method useContext = null;
        Pattern pattern = Pattern.compile(".*useContext\\('(.*)', new (.*).*\\(\\)\\);.*");

        for (PhpClass context : getContextClasses(phpClass.getProject())) {
            PsiFile file = (PsiFile)PsiTreeUtil.getParentOfType(context, PhpFile.class);
            System.out.println(file.getVirtualFile().getPath());

//            List<PhpUse> uses = PsiTreeUtil.getChildrenOfTypeAsList(file, );



            System.out.println(uses.size());

            Collection<Method> methodCalls = PsiTreeUtil.findChildrenOfType(context, Method.class);

            for (Method call : methodCalls) {
                Matcher matcher = pattern.matcher(call.getText());
                while (matcher.find()) {
                    String contextName = matcher.group(1);
                    String contextClass = matcher.group(2);
                    System.out.println(contextName + " " + contextClass);
                }
            }

//            System.out.println(useContext.getFQN());
//            PsiReference[] references = useContext.getReferences();
//            System.out.println(references.length);
//            if (references.length > 0) {
//                for (PsiReference ref : references) {
//                    System.out.println(ref);
//                }
//            }
        }

//        for (Method method : base.getMethods()) {
//            if (method.getName().equals("useContext")) {
//                useContext = method;
//                break;
//            }
//        }
//
//        if (useContext == null) {
//            return null;
//        }


//        PsiReference[] references = useContext.getReferences();


        return null;
    }

    private Method getUseContextMethod(PhpClass context) {
        for (Method method : context.getMethods()) {
            if (method.getName().equals("useContext")) {
                return method;
            }
        }

        return null;
    }

    private PhpClass getBaseContextClass(Project project) {
        Collection<PhpClass> baseClases = PhpIndex.getInstance(project).getClassesByFQN(BASE_CONTEXT_CLASS);
        if (baseClases.isEmpty()) {
            return null;
        }

        return baseClases.iterator().next();
    }

}
