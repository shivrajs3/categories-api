package nz.assurity.categories.common;


import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTestConfig {
    // A final variable to hold expected name, so that this variable can be used in multiple tests
    public final String expectedName = "Carbon credits";
    public final boolean expectedCanRelist = true;
    public final String expectedDescription="2x larger image";
    public String ENDPOINT_CATEGORIES;

    public void setUp() throws IOException {
        File src = new File(".\\src\\test\\resources\\test.properties");
        FileInputStream fileInputStream = new FileInputStream(src);
        Properties pro = new Properties();
        pro.load(fileInputStream);
        ENDPOINT_CATEGORIES = pro.getProperty("categoriesUrl");
    }
}
