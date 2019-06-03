package pl.openthejar.resource;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EmployeeResourceTest.class,
        ClientResourceTest.class
})
public class AllTests {
}
