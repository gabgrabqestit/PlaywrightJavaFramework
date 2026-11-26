package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BookingDetailsPage {
    private final Page page;
    private final Locator bookingReferenceHeading;
    private final Locator cancelBookingButton;
    private final Locator confirmCancelButton;

    public BookingDetailsPage(Page page) {
        this.page = page;
        this.bookingReferenceHeading = page.locator("main h1");
        this.cancelBookingButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Cancel Booking"));
        this.confirmCancelButton = page.locator("#confirm-dialog-yes");
    }

    public void verifyBookingDetailsPageDisplayed() {
        assertThat(page).hasURL(java.util.regex.Pattern.compile(".*/bookings/[^/]+/?$"));
        assertThat(bookingReferenceHeading).isVisible();
        assertThat(cancelBookingButton).isVisible();
    }

    public void cancelBooking() {
        cancelBookingButton.click();
        assertThat(page.getByRole(AriaRole.DIALOG,
                new Page.GetByRoleOptions().setName("Cancel this booking?"))).isVisible();
        confirmCancelButton.click();
    }
}
