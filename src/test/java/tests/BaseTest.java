package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    Playwright playwright;
    Browser browser;
    Page page;
    String baseUrl;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {

        playwright = Playwright.create();


        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        properties.load(fis);
        //mvn test -PRegression -Dbrowser=chrome -Denv=dev

        String browserName = System.getProperty("browser") !=null
                ? System.getProperty("browser")
                : properties.getProperty("browser");
        baseUrl = properties.getProperty("qa.baseUrl");
        String envName = System.getProperty("env") !=null
                ? System.getProperty("env")
                : properties.getProperty("env");

        baseUrl = properties.getProperty(envName+".baseUrl");

        if("firefox".equals(browserName)) {
          browser =  playwright.firefox().launch();
        } else if("safari".equals(browserName)) {
          browser = playwright.webkit().launch();
        } else if("chrome".equals(browserName)) {
           browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } else  {
            System.out.print("No valid browser defined in config.properties!");
        }

        page = browser.newPage();

        PlaywrightAssertions.setDefaultAssertionTimeout(7000); //Global Assertion time out 7 seconds
    }

    @AfterMethod
    public void tearDown() {

    }
}
