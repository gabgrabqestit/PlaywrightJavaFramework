package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {

    Page page;
    String baseUrl;
    private static final String email = "rahulshetty1@yahoo.com";
    private static final String password= "Magiclife1!";
    private static final String emailPlaceHolder = "you@email.com";
    private static final String passwordPlaceHolder = "Password";

    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator signInBtn;

    public LoginPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;

        this.emailInput = page.getByPlaceholder(emailPlaceHolder);
        this.passwordInput = page.getByLabel(passwordPlaceHolder);
        this.signInBtn = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Sign In"));

    }


    public void LoginToApplication() {
        page.navigate(baseUrl);

        assertThat(page).hasTitle("EventHub — Discover & Book Events");

        emailInput.fill(email);
        passwordInput.fill(password);
        signInBtn.click();
    }

}
