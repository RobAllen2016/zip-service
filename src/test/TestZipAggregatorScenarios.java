import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import zip.ZipRangeAggregator;
import zip.range.ZipRange;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class TestZipAggregatorScenarios {

    ZipRangeAggregator classUnderTest;

    @Before
    public void initialize() {
        classUnderTest = new ZipRangeAggregator();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "[94133,94133] [94200,94299] [94600,94699]", "[94133,94133] [94200,94299] [94600,94699]" },
                { "[94133,94133] [94200,94299] [94266,94399]", "[94133,94133] [94200,94399]" },
                { "[90090,90190] [90570,90600] [90020,90310] [90510,90560] [90010,90010] [90180,90500] [90590,90600] [90180,90500]",
                        "[90010,90010] [90020,90500] [90510,90560] [90570,90600]" },
                { "[06430,06699] [21222,24998]  [80376,80376]  [02134,06431]", "[02134,06699] [21222,24998] [80376,80376]"}
        });
    }

    // Control attributes
    private String inputZipRanges;
    private String expectedResult;

    public TestZipAggregatorScenarios(String inputZipRanges, String expectedResult) {
        this.inputZipRanges = inputZipRanges;
        this.expectedResult = expectedResult;
    }

    @Test
    public void testZipAggregator() {
        System.out.println("Testing for: " + inputZipRanges);
        List<ZipRange> inputList = classUnderTest.parseRangeString(inputZipRanges);
        String result = classUnderTest.makeListOfPairs(classUnderTest.aggregate(inputList));
        System.out.print(" ... result is " + result);
        System.out.println(" - expected " + expectedResult);
        System.out.println(">>> RESULT " + (expectedResult.trim().equals(result.trim()) ? "EQUALS" : "DOES NOT EQUAL") +" EXPECTED VALUE");
        assert expectedResult.trim().equals(result.trim());
    }
}
