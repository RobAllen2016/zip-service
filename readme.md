# ZIP Code Range Aggregator

## Overview of Building Out This Exercise

I started approaching this with the intent of delivering a web service to meet the requirements.  I had the base logic
completed in about an hour and 20 minutes, working, and then spent way too much time building it into a basic service.

## Brass Tacks: How to run it.

There are two-and-a-half ways to pass data into this solution and receive results back:

1. Run the jar.  This app was built using Spring Boot and compiles down to an executable jar.  Run the jar in the
build/libs folder and it will spin up the Spring Boot Actuator, listening at localhost to port 8090 (and 8091
for management).  The input sequence of pairs is passed in as a request parameter called "ranges," so a valid GET
request looks like: <http://localhost:8090/zip-aggregator?ranges=[94133,94133] [94200,94299]>
    - The "half" way is that you run the jar and then open the **manual-test.html** file in a browser (which is in the
same path as this readme file).  It has links to run three canned scenarios.
2. Run the JUnit test.  There's a parameterized unit test under src/test that will run the aggregator without needing
to start the jar or use a browser.  If you want to add more test scenarios, just edit the parameter collection with the
input value first and the expected value second.

## Caveats

####Some things to keep in mind:
* I started down the path of consuming and producing JSON, but ultimately decided it didn't meet the problem's
criteria of using restrictions "stored as a series of ranges of 5 digit codes".  Instead (and for expedience) I landed
on the text string as request parameter, response string as response body.  This could very easily be modified to pass
around JSON.
* I decided not to do a lot of validation, and as a consequence to not put a lot of effort into pretty error messages.
The app expects a series of paired values enclosed in square brackets, separated by whitespace.  The numbers inside the
brackets must be 5-digit integers.  Any pairs that don't match the criteria (e.g. have only 4 digits or have inline
alphabetic characters) are just ignored.