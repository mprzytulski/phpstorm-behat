package pl.projectspace.idea.plugins.php.behat.service;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.*;
import pl.projectspace.idea.plugins.php.behat.psi.element.BehatContextClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextLocator extends ProjectRelatedService {

    public static final String BASE_CONTEXT_CLASS = "\\Behat\\Behat\\Context\\BehatContext";

    public Collection<BehatContextClass> getContextClasses(){
        Collection<BehatContextClass> contextClasses = new ArrayList<BehatContextClass>();

        for(PhpClass c : index.getAllSubclasses(BASE_CONTEXT_CLASS)) {
            contextClasses.add(new BehatContextClass(c));
        }

        return contextClasses;
    }

    public PhpClass getMainContextFor(PhpClass phpClass) {
        PhpClass base = getBaseContextClass(phpClass.getProject());

        if (base == null) {
            return null;
        }

        for (BehatContextClass context : getContextClasses()) {
            PhpFile file = (PhpFile)PsiTreeUtil.getParentOfType(context.getPhpClass(), PhpFile.class);

            HashMap<String, ClassReference> references = new HashMap<String, ClassReference>();

            getReferences(getUseContextMethod(phpClass), context.getPhpClass(), references);

            for (ClassReference r : references.values()) {
                if (r.getFQN().equals(phpClass.getFQN())) {
                    Collection<PhpClass> contextClasses = index.getClassesByFQN(r.getFQN());

                    if (contextClasses.isEmpty()) {
                        return null;
                    }

                    return contextClasses.iterator().next();
                }
            }
        }

        return null;
    }

    private void getReferences(Method method, PsiElement element, Map<String, ClassReference> references) {

        // check current item
        checkReference(element, method, references);

        // walk down
        for (PsiElement e : element.getChildren()) {
            checkReference(e, method, references);
            getReferences(method, e, references);
        }

        // walk right
        PsiElement e = element;
        do {
            checkReference(e, method, references);
        } while ((e = e.getNextSibling()) != null);

    }

    private void checkReference(PsiElement element, Method method, Map<String, ClassReference> references) {
        if (isMethod(element, method)) {
            MethodReference r = (MethodReference)element;
            StringLiteralExpression name = (StringLiteralExpression)r.getParameters()[0];
            NewExpression type = (NewExpression)r.getParameters()[1];
            if (!references.containsKey(name.getContents())) {
                references.put(name.getContents(), type.getClassReference());
            }
        }
    }

    private boolean isMethod(PsiElement element, Method method) {
        if (element instanceof Method) {
            return ((Method)element).getName().equals(method.getName());
        }
        else if (element instanceof MethodReference) {
            return ((MethodReference)element).getName().equals(method.getName());
        }

        return false;
    }

    public PhpClass getSubContextFor(PhpClass phpClass, String alias) {
        PhpClass base = getBaseContextClass(phpClass.getProject());

        if (base == null) {
            return null;
        }

        for (BehatContextClass context : getContextClasses()) {
            HashMap<String, ClassReference> references = new HashMap<String, ClassReference>();

            getReferences(getUseContextMethod(phpClass), context.getPhpClass(), references);

            for (String contextName : references.keySet()) {
                if (contextName.equals(alias)) {
                    Collection<PhpClass> contextClasses = index.getClassesByFQN(references.get(alias).getFQN());

                    if (contextClasses.isEmpty()) {
                        return null;
                    }

                    return contextClasses.iterator().next();
                }
            }
        }

        return null;
    }

    public HashMap<String, ClassReference> getSubContextFor(PhpClass phpClass) {
        PhpClass base = getBaseContextClass(phpClass.getProject());

        if (base == null) {
            return null;
        }

        HashMap<String, ClassReference> references = new HashMap<String, ClassReference>();

        for (BehatContextClass context : getContextClasses()) {

            getReferences(getUseContextMethod(phpClass), context.getPhpClass(), references);
        }

        return references;
    }


    public boolean isContextClass(PhpClass phpClass) {
        if (phpClass.getFQN().equals(BASE_CONTEXT_CLASS)) {
            return true;
        }

        for (PhpClass parent : phpClass.getSupers()) {
            if (parent.getFQN().equals(BASE_CONTEXT_CLASS)) {
                return true;
            }
            for (PhpClass p : parent.getSupers()) {
                if (isContextClass(p)) {
                    return true;
                }
            }
        }

        return false;
    }

//    private boolean is

    private PhpClass getClassFromFile(final String name, final Map<String, PhpNamedElement> classMap) {
        for (String className : classMap.keySet()) {
            if (className.equals(name) && (classMap.get(name) instanceof PhpClass)) {
                return (PhpClass) classMap.get(name);
            }
        }

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
        Collection<PhpClass> baseClasses = index.getClassesByFQN(BASE_CONTEXT_CLASS);
        if (baseClasses.isEmpty()) {
            return null;
        }

        return baseClasses.iterator().next();
    }

}
