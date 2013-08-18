package pl.projectspace.idea.plugins.php.behat.service.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class InvalidMethodArgumentsException extends Exception{
    public InvalidMethodArgumentsException(String message) {
        super(message);
    }
}
