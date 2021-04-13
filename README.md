test-automation-MENU Project:

java MENU Project for UI automation testing.


Concepts Included : 

TestNG framework
Page Object pattern
Common web page interaction methods


Tools :

Maven
Selenium Webdriver
Eclipse
JDK 11
chromedriver

Requirements

In order to utilise this project you need to have the following installed locally:

Chrome (UI tests use Chrome by default) and chromedriver (which is already added inside the project)
JDK 11
Maven 3


Usage

Download the zip or clone the Git repository.
Unzip the zip file (if you downloaded one).
Open Command Prompt and Change directory (cd) to folder containing pom.xml

To run test navigate to MenuWebApp directory and run command in cmd (Command Prompt):

mvn clean test


Another option to run the test go to : 

directory run_TestNg\bat\test and double click on testng.bat test will start automatically 

Reporting

Reports for this module are written into  /Reports directory after a successful run.
UI tests result in a HTML report, PDF report and CSV report.
In the case of test failures, a screen-shoot of the UI at the point of failure is embedded into the report.





