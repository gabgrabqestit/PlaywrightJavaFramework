package tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class FrameworkBuildTest extends BaseTest{

    @Test(description = "Create Event -Book that event and verify if its booked")
    public void DemoTest() {

        String eventTitle = "gabs workshop";
        String description = "A testing workshop in playwright";
        String category = "Concert";
        String city = "London";
        String venue = "Park avenue 12";
        String dateTime = "2026-12-18T07:25";
        String price = "100";
        String seats = "50";

        LoginPage loginPage = new LoginPage(page, baseUrl);
        DashboardPage dashboardPage = new DashboardPage(page);
        AdminEventsPage adminEventsPage = new AdminEventsPage(page);
        CreateEventPage createEventPage = new CreateEventPage(page);
        EventsPage eventsPage = new EventsPage(page, eventTitle);
        BookTicketsPage bookTicketsPage = new BookTicketsPage(page);

        loginPage.LoginToApplication();
        dashboardPage.waitForEventsToLoad();
        adminEventsPage.navigateToEventsPage();
        createEventPage.createEvent(
                eventTitle,
                description,
                category,
                city,
                venue,
                dateTime,
                price,
                seats);

        //Step 2 - Find newly created event in the events page
        eventsPage.goToEvents();
        eventsPage.waitForEventsToLoad();

        //Visibility of the card which we have added
       eventsPage.findEventCard(eventTitle);
       int seatAmountBeforeBooking = eventsPage.getSeats();
       eventsPage.goToBookEvent();

        //book a ticket
        bookTicketsPage.bookEvent("max Muster", "testemail@test", "1234567890");

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
