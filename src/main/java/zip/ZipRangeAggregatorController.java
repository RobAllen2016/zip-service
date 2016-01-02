package zip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zip.range.ZipRange;

import java.util.List;

/**
 * REST Controller for the Spring Boot application
 */
@Controller
@RequestMapping("/zip-aggregator")
public class ZipRangeAggregatorController {

    @Autowired
    ZipRangeAggregator zipRangeAggregator;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String aggregateRanges(@RequestParam("ranges") String ranges) {
        List<ZipRange> inputList = zipRangeAggregator.parseRangeString(ranges);
        return zipRangeAggregator.makeListOfPairs(zipRangeAggregator.aggregate(inputList));
    }

    @RequestMapping(path = "/about", method = RequestMethod.GET)
    public @ResponseBody String about() {
        return new AppAbout().getContent();
    }

}
