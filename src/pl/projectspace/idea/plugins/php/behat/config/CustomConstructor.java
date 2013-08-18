package pl.projectspace.idea.plugins.php.behat.config;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.nodes.Node;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class CustomConstructor extends AbstractConstruct {
    @Override
    public Object construct(Node node) {
        return null;
    }
}
