package pl.openthejar.resource;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Przeprowadza wszystkie testy
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EmployeeResourceTest.class,
        ClientResourceTest.class,
        ProductResourceTest.class,
        ServiceResourceTest.class,
        WorkDateResourceTest.class,
        DiscountResourceTest.class
})
public class AllTests {
}
