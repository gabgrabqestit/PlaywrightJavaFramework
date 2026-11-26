package tests;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.DataProviderUtil;

import java.io.IOException;
import java.util.HashMap;

public class FrameworkBuildDataDrivenTest extends BaseTest{

    @DataProvider(name="eventBookingData")
    public Object[][] eventBookingData() throws IOException {
      return DataProviderUtil.getJsonDataToMap("src/test/resources/eventBookingData.json");
    }

    @Test(groups= {"framework"},dataProvider = "eventBookingData", description = "Create Event -Book that event and verify if its booked")
    public void DemoTest(HashMap<String, String> data) {

        LoginPage loginPage = new LoginPage(page, baseUrl);
        DashboardPage dashboardPage = new DashboardPage(page);
        AdminEventsPage adminEventsPage = new AdminEventsPage(page);
        CreateEventPage createEventPage = new CreateEventPage(page);
        EventsPage eventsPage = new EventsPage(page, data.get("titlePrefix"));
        BookTicketsPage bookTicketsPage = new BookTicketsPage(page);

        loginPage.LoginToApplication();
        dashboardPage.waitForEventsToLoad();
        adminEventsPage.navigateToEventsPage();
        createEventPage.createEvent(
                data.get("titlePrefix"),
                data.get("description"),
                data.get("category"),
                data.get("city"),
                data.get("venue"),
                data.get("dateTime"),
                data.get("price"),
                data.get("totalSeats"));

        //Step 2 - Find newly created event in the events page
        eventsPage.goToEvents();
        eventsPage.waitForEventsToLoad();

        //Visibility of the card which we have added
       eventsPage.findEventCard(data.get("titlePrefix"));
       int seatAmountBeforeBooking = eventsPage.getSeats();
       eventsPage.goToBookEvent();

        //book a ticket
        bookTicketsPage.bookEvent(data.get("fullName"), data.get("email"), data.get("phone"));

        //-------------------------------------------------------------------------------------------------
        String bookingRef = eventsPage.getBookingReference();
        eventsPage.goToBookingOverview();
        eventsPage.verifyBooking();

        eventsPage.goToEvents();
        eventsPage.waitForEventsToLoad();
        page.waitForTimeout(2000);

        Locator eventCardsAfter = page.getByTestId("event-card"); //{loc1,loc2,loc3,loc4}
        Locator targetCardAfter = eventCardsAfter.filter(new Locator.FilterOptions().setHasText("gabs workshop"));
        String seatsTextUpdated = targetCardAfter.getByText("seats").innerText();
        System.out.println(seatsTextUpdated);

        int seatAmountAfterBooking = eventsPage.getSeats();
        Assert.assertTrue(seatAmountBeforeBooking > seatAmountAfterBooking);
    }
}
