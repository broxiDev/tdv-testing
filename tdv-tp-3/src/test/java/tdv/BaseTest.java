package tdv;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected static WebDriver driver;

    @BeforeAll
    static void setUp() {
        driver = DriverManager.getDriver();
    }

    @AfterAll
    static void tearDown() {
        DriverManager.quitDriver();
    }
}
