package _run;

import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;

public class TestNGRunner {
	

	static TestNG testNg;

	public static void main(String[] args) {
		
	
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		//Turning off test-output in TestNG
		testng.setUseDefaultListeners(false);
		List<String> suites = Lists.newArrayList();
		suites.add("testng.xml");//path to xml..
	
		testng.setTestSuites(suites);
		testng.run();
		
		 }
		 }

	

