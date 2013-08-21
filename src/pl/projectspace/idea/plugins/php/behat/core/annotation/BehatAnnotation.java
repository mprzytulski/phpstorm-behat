package pl.projectspace.idea.plugins.php.behat.core.annotation;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatAnnotation {

    public static final String[] step = {"Given", "When", "Then"};

    public static final String[] hook = {"BeforeSuite", "AfterSuite", "BeforeFeature", "AfterFeature", "BeforeScenario", "AfterScenario", "BeforeStep", "AfterStep"};

    public static final String[] tags = {"sprint:", "javascript", "mink:goutte", "mink:zombie", "mink:sahi", "mink:selenium2", "postponed", "slow", "wip", "missing-edge-case"};

}
