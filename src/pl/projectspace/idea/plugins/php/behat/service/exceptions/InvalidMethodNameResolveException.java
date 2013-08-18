package pl.projectspace.idea.plugins.php.behat.service.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class InvalidMethodNameResolveException extends Exception{
    public InvalidMethodNameResolveException(String message) {
        super(message);
    }
}
