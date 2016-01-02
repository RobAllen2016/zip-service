package zip;

/**
 * Return the about content.  If this were anything other than an exercise, I'd probably put this class behind
 * an interface and make it a Spring component with a qualifier to allow content to be retrieved from configurable
 * sources, like a text file or a property file or a content management system.  Since this is just an evaluation,
 * though, I'm just using static text.
 */
public class AppAbout {

    /**
     * The static text to be returned when the About content is requested.
     */
    private static final String content =
            "<H2>About ZIP Code Restriction Range Aggregator Service</H2>" +
            "Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as " +
            "a series of ranges of 5 digit codes. These ranges represent ZIP codes to which items <i>cannot</i> be " +
            "shipped.<p/>" +
            "This service takes an arbitrary-length series of 5-digit codes (which, because they're US Zip Codes, may " +
            "begin with a '0') and aggregates them, returning the minimum set of ranges that represdent the same " +
            "restriction as the input ranges.  In other words, the service consolidates the ranges and removes any " +
            "overlaps.<p/>";

    /**
     * Return the content.
     *
     * @return String content with embedded HTML.
     */
    public String getContent() {
        return content;
    }
}
