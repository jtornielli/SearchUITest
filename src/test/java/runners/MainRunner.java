package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions (features = {"classpath:features"}, glue = {"stepDefinitions"},
        monochrome = true ,dryRun = false,plugin = {"pretty","html:target/cucumber"})

public class MainRunner extends AbstractTestNGCucumberTests{
}
