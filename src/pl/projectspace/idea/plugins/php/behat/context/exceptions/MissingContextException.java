package pl.projectspace.idea.plugins.php.behat.context.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class MissingContextException extends Exception {
    public MissingContextException(String message) {
        super(message);
    }
}
