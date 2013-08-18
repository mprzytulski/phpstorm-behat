package pl.projectspace.idea.plugins.php.behat.config.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import pl.projectspace.idea.plugins.php.behat.config.Behat;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatConverter implements JsonDeserializer<Behat> {

    @Override
    public Behat deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Behat behat = new Behat();

        for (Map.Entry<String, JsonElement>element : jsonElement.getAsJsonObject().entrySet()) {
//            behat.addProfile(element.getKey(), (Profile)element.getValue());
            System.out.println(element.getValue());
        }

        return behat;
    }
}
