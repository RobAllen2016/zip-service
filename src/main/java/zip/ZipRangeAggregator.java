package zip;

import org.springframework.stereotype.Component;
import zip.range.ZipRange;

import java.util.*;

/**
 * This is essentially a helper or business logic class that does the gruntwork for the app.
 */
@Component
public class ZipRangeAggregator {

    private static final String PAIR_FORMAT_REGEX = "\\[\\d{5}\\,\\d{5}\\]";

    /**
     * Take a string of min/max pairs in the format [99999,99999] separated by whitespace, and convert it to a list
     * of ZipRanges.  This is a very, very rigid conversion algorithm for the purpose of the exercise.  Were I to
     * spend more time on this, I'd make it much more robust - validating numbers, handling whitespace inside the brackets,
     * handling too large numbers, etc.  Based on the brief problem description, though, I didn't deem it worth the time
     * to make this bulletproof. Poorly formed pairs will throw exceptions and result in a 500 response.
     *
     * @param ranges A string of range pairs, e.g. "[94133,94133] [94200,94299] [94226,94399]"
     * @return Returns a list of ZipRange objects.
     */
    public List<ZipRange> parseRangeString(String ranges) {
        String[] pairs = ranges.split("\\s+");

        LinkedList<ZipRange> outList = new LinkedList<ZipRange>();
        for (String pair:pairs) {
            if (pair.trim().matches(PAIR_FORMAT_REGEX)) {
                // As long as we meet the regex, we know exactly where the numbers are.  If the numbers are formatted
                // badly, this will throw a number format exception.
                int min = Integer.parseInt(pair.trim().substring(1,6));
                int max = Integer.parseInt(pair.trim().substring(7,12));

                outList.add(new ZipRange(min, max));
            }
        }
        return outList;
    }

    /**
     * Convert the ZipRange objects into a list of ordered pairs as dictated by the problem statement.
     *
     * @param outList
     * @return
     */
    public String makeListOfPairs(List<ZipRange> outList) {
        String returnValue = "";

        for (ZipRange z: outList) {
            returnValue = returnValue + z.toString() + " ";
        }

        return returnValue;
    }

    /**
     * Aggregate an entire list of ranges.  See below for the logical workflow steps.
     *
     * @param inputList List of ZipRange instances.
     * @return A new list of aggregated ZipRange instances.
     */
    public List<ZipRange> aggregate(final List<ZipRange> inputList) {
        List<ZipRange> outputList = new LinkedList<ZipRange>();

        // Let's start by sorting the input list.  We don't want to mutate our argument, so we'll make a copy first.
        List<ZipRange> sortedList = new ArrayList<ZipRange>(inputList.size());
        sortedList.addAll(inputList);
        Collections.sort(sortedList);

        // Start at the beginning, then begin comparing.  Since we now have a list sorted by minValue, the logic
        // goes something like this:
        // - Set the first node to your "working copy".
        // - Compare your working copy to the next node in the list.
        //     = if they overlap, aggregate them and set the new aggregate to your working copy
        //     = if they don't overlap, add what you've been working with to the output list, grab the next node and continue.
        // - Repeat until you run out of list.
        // - The way the look is structured, we'll want to take our last working copy and add it to the output list
        //   after we exit the loop.
        ZipRange principal = sortedList.get(0);
        for (int i=1; i < sortedList.size(); i++) {
            ZipRange subject = sortedList.get(i);

            if (principal.overlaps(subject)) {
                principal = principal.aggregateWithZipRange(subject);
            } else {
                outputList.add(principal);
                principal = subject;
            }
        }
        outputList.add(principal);

        return outputList;
    }
}
