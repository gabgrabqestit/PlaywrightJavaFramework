package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class MoreUIValidationTest {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        context = browser.newContext();

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");
    }

    @Test
    public void childWindowHandle() {
        Locator blinkingText = page.locator(".blinkingText");
        Locator firstLink = blinkingText.first();

        Page newPage = context.waitForPage(firstLink::click);
        newPage.waitForLoadState();

        Locator documentRequestEmailTextContainer = newPage.locator(".red");
        String documentRequestEmailText = documentRequestEmailTextContainer.textContent();


        String email = documentRequestEmailText.split("at ")[1].split(" ")[0];
        System.out.println(email);

        Locator usernameInput = page.getByLabel("Username:");
        usernameInput.fill(email);
        System.out.println(usernameInput.inputValue());
    }

    @Test(groups = {"smoke"})
    public void uiControls() {
        Locator userRdBtn = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("User"));
        Locator okayBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Okay"));

        userRdBtn.click();
        okayBtn.click();

        Assert.assertTrue(userRdBtn.isChecked());

        Locator checkboxTerms = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("I agree to the terms and conditions"));

        checkboxTerms.check();

        page.getByRole(AriaRole.COMBOBOX).selectOption("Teacher");


    }

    @AfterMethod()
    public void teardown() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
    }
}
