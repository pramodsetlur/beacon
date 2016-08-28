/**
 * Copyright (C) 1999 - 2014 Ticketmaster
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */
package hackathon.slashhack.bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/",
        format = {"json:target/results/result.json", "html:target/cucumber-html"})
public class RunnerIT {
    @ClassRule
    public static final ServiceRule SERVICE = new ServiceRule();
}
