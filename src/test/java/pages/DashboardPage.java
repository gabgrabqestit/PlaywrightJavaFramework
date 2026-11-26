package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DashboardPage {
    Page page;
    private final Locator browseEventsLnk;
    private final Locator myBookingsTab;

    public DashboardPage(Page page) {
        this.page = page;
        this.browseEventsLnk = page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Browse Events →"));
        this.myBookingsTab = page.locator("#nav-bookings");
    }

    public void waitForEventsToLoad() {
        assertThat(browseEventsLnk).isVisible();
    }

    public void goToMyBookings() {
        myBookingsTab.click();
    }
}
