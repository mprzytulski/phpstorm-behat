package pl.projectspace.idea.plugins.php.behat.core;

import pl.projectspace.idea.plugins.commons.php.code.inspection.GenericMethodParameterInspection;
import pl.projectspace.idea.plugins.php.behat.core.annotations.BehatCondition;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class BehatMethodParameterInspection extends GenericMethodParameterInspection {

    @Override
    @BehatCondition
    protected boolean isEnabled() {
        return false;
    }
}
