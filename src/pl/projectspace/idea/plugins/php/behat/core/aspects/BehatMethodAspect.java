package pl.projectspace.idea.plugins.php.behat.core.aspects;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.ProcessingContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.core.annotations.BehatAction;
import pl.projectspace.idea.plugins.php.behat.core.annotations.BehatCondition;
import pl.projectspace.idea.plugins.php.behat.core.annotations.BehatNameProvider;
import pl.projectspace.idea.plugins.php.behat.core.annotations.BehatReferenceProvider;

import java.util.Collections;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
@Aspect
public class BehatMethodAspect {

    @Around("execution(* *.getReferencesByElement(..)) && @annotation(behatReferenceProviderAnnotation)")
    public PsiReference[] returnEmptyReferenceArray(BehatReferenceProvider behatReferenceProviderAnnotation, ProceedingJoinPoint jp, JoinPoint.EnclosingStaticPart enc) throws Throwable {
        if (!BehatProject.isEnabled()) {
            return new PsiReference[0];
        }
        return (PsiReference[])jp.proceed();
    }

    @Around("execution(* *.*(..)) && @annotation(condition)")
    public boolean returnBehatStatus(BehatCondition condition, ProceedingJoinPoint jp, JoinPoint.EnclosingStaticPart enc) throws Throwable {
        return BehatProject.isEnabled();
    }

    @Around("execution(* *.getCompletions(..)) && @annotation(condition)")
    public List<String> returnBehatStatus(BehatNameProvider condition, ProceedingJoinPoint jp, JoinPoint.EnclosingStaticPart enc) throws Throwable {
        if (!BehatProject.isEnabled()) {
            return Collections.emptyList();
        }
        return (List<String>) jp.proceed();
    }

    @Around("execution(* *.update(com.intellij.openapi.actionSystem.AnActionEvent)) && args(anActionEvent) && @annotation(action)")
    public void disablePresentation(BehatAction action, com.intellij.openapi.actionSystem.AnActionEvent anActionEvent, ProceedingJoinPoint jp, JoinPoint.EnclosingStaticPart enc) throws Throwable {
        if (!BehatProject.isEnabled()) {
            anActionEvent.getPresentation().setVisible(false);
            anActionEvent.getPresentation().setEnabled(false);
            return;
        }
        jp.proceed();
    }

}
