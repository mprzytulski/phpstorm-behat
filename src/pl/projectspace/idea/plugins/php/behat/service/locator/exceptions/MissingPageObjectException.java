package pl.projectspace.idea.plugins.php.behat.service.locator.exceptions;

import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class MissingPageObjectException extends MissingElementException {
    public MissingPageObjectException(String message) {
        super(message);
    }
}
