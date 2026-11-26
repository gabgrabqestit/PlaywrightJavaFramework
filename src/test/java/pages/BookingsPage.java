package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BookingsPage {
    private final Page page;
    private final Locator pageHeading;
    private final Locator viewDetailsLinks;

    public BookingsPage(Page page) {
        this.page = page;
        this.pageHeading = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("My Bookings"));
        this.viewDetailsLinks = page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("View Details"));
    }

    public void verifyBookingsPageDisplayed() {
        assertThat(pageHeading).isVisible();
        assertThat(page).hasURL(java.util.regex.Pattern.compile(".*/bookings/?$"));
    }

    public void viewFirstBookingDetails() {
        assertThat(viewDetailsLinks.first()).isVisible();
        viewDetailsLinks.first().click();
    }
}
