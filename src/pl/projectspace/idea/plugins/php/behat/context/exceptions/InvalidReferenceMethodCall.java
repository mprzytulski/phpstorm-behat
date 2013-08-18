package pl.projectspace.idea.plugins.php.behat.context.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class InvalidReferenceMethodCall extends Exception {
    public InvalidReferenceMethodCall(String message) {
        super(message);
    }
}
