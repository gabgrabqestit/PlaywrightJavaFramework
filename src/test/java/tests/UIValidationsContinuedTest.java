package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UIValidationsContinuedTest {

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
        page.navigate("https://rahulshettyacademy.com/AutomationPractice/");
    }

    @Test(groups = {"smoke"})
    public void popupValidations() {
        Locator hideInput = page.getByPlaceholder("Hide/Show Example");
        assertThat(hideInput).isVisible();

        Locator hideTextboxBtn = page.locator("#hide-textbox");
        hideTextboxBtn.click();
        assertThat(hideInput).isHidden();

        page.onDialog(Dialog::accept);

        Locator alertBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(("Alert")));
        alertBtn.click();

        Locator hoverBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Mouse Hover"));
        hoverBtn.hover();

        Locator topSelection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Top"));
        //-------------------------------------------------------------------------
        FrameLocator framesPage = page.frameLocator("#courses-iframe");
        //-------------------------------------------------------------------------
        Locator learningPathsLnk = framesPage.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName("Learning Paths"));
        learningPathsLnk.click();

        String textCheck = framesPage.locator(".inner-box h1").textContent();
        System.out.println(textCheck);

    }

    @Test
    public void screenShotTest() {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("pagescreenshot.png")));
        Locator hideInput = page.getByPlaceholder("Hide/Show Example");

        hideInput.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("editboxscreenshot.png")));

    }

    @AfterMethod()
    public void teardown() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
    }

}
