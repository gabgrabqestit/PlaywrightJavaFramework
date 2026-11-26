package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage {
    private final Page page;
    private final Locator heroHeading;
    private final Locator browseEventsLink;
    private final Locator heroMyBookingsButton;
    private final Locator viewAllEventsLink;
    private final Locator exploreAllEventsButton;
    private final Locator featuredEventsHeading;
    private final Locator eventCards;

    public HomePage(Page page) {
        this.page = page;
        this.heroHeading = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Discover & Book Amazing Events"));
        this.browseEventsLink = page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Browse Events →"));
        this.heroMyBookingsButton = page.locator("main").getByRole(AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName("My Bookings"));
        this.viewAllEventsLink = page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("View all →"));
        this.exploreAllEventsButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Explore All Events"));
        this.featuredEventsHeading = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Featured Events"));
        this.eventCards = page.getByTestId("event-card");
    }

    public void verifyHomePageDisplayed() {
        assertThat(page).hasTitle("EventHub — Discover & Book Events");
        assertThat(heroHeading).isVisible();
        assertThat(browseEventsLink).isVisible();
        assertThat(heroMyBookingsButton).isVisible();
    }

    public void verifyFeaturedEventsDisplayed() {
        assertThat(featuredEventsHeading).isVisible();
        assertThat(eventCards.first()).isVisible();
    }

    public void verifyFirstFeaturedCardLinksAreConsistent() {
        Locator firstEventCard = eventCards.first();
        assertThat(firstEventCard).isVisible();

        Locator eventTitle = firstEventCard.locator("h3");
        Locator eventDetailsLink = firstEventCard.getByRole(AriaRole.LINK).first();
        Locator bookNowLink = firstEventCard.getByRole(AriaRole.LINK,
                new Locator.GetByRoleOptions().setName("Book Now"));

        assertThat(eventTitle).isVisible();
        assertThat(bookNowLink).isVisible();

        String eventDetailsHref = eventDetailsLink.getAttribute("href");
        String bookNowHref = bookNowLink.getAttribute("href");
        if (eventDetailsHref == null || !eventDetailsHref.equals(bookNowHref)) {
            throw new AssertionError("Event title and Book Now links should lead to the same event");
        }
    }

    public void openFirstFeaturedEvent() {
        eventCards.first().getByRole(AriaRole.LINK).first().click();
    }

    public String getFirstFeaturedEventTitle() {
        return eventCards.first().locator("h3").innerText();
    }

    public void clickBrowseEvents() {
        browseEventsLink.click();
    }

    public void clickHeroMyBookings() {
        heroMyBookingsButton.click();
    }

    public void clickViewAllEvents() {
        viewAllEventsLink.click();
    }

    public void clickExploreAllEvents() {
        exploreAllEventsButton.click();
    }

    public void returnToHomePage() {
        page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("EventHub")).click();
    }
}
