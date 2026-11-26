package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BookTicketsPage {
    Page page;

    private final Locator fullNameInput;
    private final Locator emailInput;
    private final Locator phoneNumberInput;
    private final Locator confirmBookingBtn;

    public BookTicketsPage(Page page) {

        this.page = page;
        this.fullNameInput = page.getByLabel("Full name");
        this.emailInput = page.getByLabel("Email");
        this.phoneNumberInput = page.getByLabel("Phone Number");
        this.confirmBookingBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Booking"));
    }

    public void bookEvent(String name, String email, String phoneNumber) {
        fullNameInput.fill("Max Muster");
        emailInput.fill("maxmuster@mail.com");
        phoneNumberInput.fill("1234567890");
        confirmBookingBtn.click();

        assertThat(page.getByText("Your tickets are reserved.")).isVisible();
    }

}
