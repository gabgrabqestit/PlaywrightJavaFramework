package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EventsPage {
    Page page;
    String cardTitle;

    private final Locator eventCards;
    private final Locator targetCard;
    private final Locator bookingReference;
    private final Locator viewBookingsBtn;
    private final Locator bookingCards;
    private final Locator targetBookingCard;
    private String bookingRef;


    public EventsPage(Page page, String cardTitle) {
        this.page = page;
        this.bookingCards = page.locator("#booking-card");
        this.cardTitle = cardTitle;
        this.eventCards = page.getByTestId("event-card");
        this.targetCard = eventCards.filter(new Locator.FilterOptions().setHasText(cardTitle));
        this.bookingReference = page.locator(".booking-ref");

        this.viewBookingsBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View my Bookings"));
        this.targetBookingCard = bookingCards.filter(new Locator.FilterOptions().setHasText(bookingRef));
    }

    public String getBookingReference() {
        this.bookingRef = bookingReference.innerText();
        return bookingRef;
    }

    public void goToEvents() {
        page.locator("#nav-events").click();
    }

    public void waitForEventsToLoad() {
        assertThat(eventCards.first()).isVisible();
    }

    public void findEventCard(String cardTitle) {
        assertThat(targetCard).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
    }

    public int getSeats() {
        String seatsText = targetCard.getByText("seats").innerText();
        int seatsCount = Integer.parseInt(seatsText.split(" ")[0]);
        return seatsCount;
    }

    public void goToBookEvent() {
        targetCard.getByTestId("book-now-btn").click();
    }

    public void goToBookingOverview() {
        viewBookingsBtn.click();
    }

    public void verifyBooking() {
        Locator targetBookingCard = bookingCards.filter(new Locator.FilterOptions().setHasText(bookingRef));
        assertThat(targetBookingCard).isVisible();
    }
}
