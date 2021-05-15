package nz.assurity.categories.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "/test.properties")
public class BaseTestConfig {
    // A final variable to hold expected name, so that this variable can be used in multiple tests
    public final String expectedName = "Carbon credits";
    public final boolean expectedCanRelist = true;
    public final String expectedDescription="2x larger image";

    @Value("${categoriesUrl}")
    public String ENDPOINT_CATEGORIES;
}
