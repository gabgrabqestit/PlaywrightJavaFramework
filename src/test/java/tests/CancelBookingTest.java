package tests;

import org.testng.annotations.Test;
import pages.BookingDetailsPage;
import pages.BookingsPage;
import pages.DashboardPage;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CancelBookingTest extends BaseTest {

    @Test(description = "Open the first booking and cancel it")
    public void cancelFirstBooking() {
        LoginPage loginPage = new LoginPage(page, baseUrl);
        DashboardPage dashboardPage = new DashboardPage(page);
        BookingsPage bookingsPage = new BookingsPage(page);
        BookingDetailsPage bookingDetailsPage = new BookingDetailsPage(page);

        loginPage.LoginToApplication();

        // Home page checkpoint after sign-in
        dashboardPage.waitForEventsToLoad();

        dashboardPage.goToMyBookings();
        bookingsPage.verifyBookingsPageDisplayed();
        bookingsPage.viewFirstBookingDetails();

        bookingDetailsPage.verifyBookingDetailsPageDisplayed();
        bookingDetailsPage.cancelBooking();

        assertThat(page.getByText("Booking cancelled successfully")).isVisible();
    }
}
