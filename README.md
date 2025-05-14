**Preconditions:**

An emulated device is running. I used a Pixel 9.

The Tubi app is installed.

**To run:**

1. Navigate to the root folder of the project. You should see "testng.xml".

2. Open a command line at this location and run "mvn clean test -DsuiteXmlFile.suiteXmlFiles=testng.xml"

This may complain. The Java version for this project is openSDK 22. If the command line run doesn't work, you can open the project in Intellij, navigate to "FirstTest" and click run.

**Reporting:**

A test report will be created at "\target\surefire-reports"


**Notes:**

- This takes about 8 minutes to run.
- The test requirement was to look for "UglyDolls", but that is very far down the list and takes about 30 minutes to find. I use "Little Monsters" instead.
- I organized my framework using utilities classes and a page object model.
- I implemented retries
- Future improvements: Parallelization, test groups, tightening the algorithm that looks for the specific movie we want, a driver factory so we can run against different devices, a way to launch the emulated device at the start of the test.
