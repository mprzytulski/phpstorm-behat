package pl.projectspace.idea.plugins.php.behat.code.annotation;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatAnnotation {

    public static final String[] step = {"Given", "When", "Then"};

    public static final String[] hook = {"BeforeSuite", "AfterSuite", "BeforeFeature", "AfterFeature", "BeforeScenario", "AfterScenario", "BeforeStep", "AfterStep"};

}
