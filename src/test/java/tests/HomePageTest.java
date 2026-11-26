package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePageTest extends BaseTest {

    @Test(groups = {"smoke"}, description = "Verify the home page and its featured events")
    public void homePageDisplaysItsPrimaryContent() {
        signIn();

        HomePage homePage = new HomePage(page);
        homePage.verifyHomePageDisplayed();
        homePage.verifyFeaturedEventsDisplayed();
    }

    @Test(groups = {"smoke"}, description = "Verify the home page calls to action navigate correctly")
    public void homePageCallsToActionNavigateToTheirDestinations() {
        signIn();

        HomePage homePage = new HomePage(page);
        homePage.verifyHomePageDisplayed();

        homePage.clickBrowseEvents();
        verifyEventsPage();

        homePage.returnToHomePage();
        homePage.clickViewAllEvents();
        verifyEventsPage();

        homePage.returnToHomePage();
        homePage.clickExploreAllEvents();
        verifyEventsPage();

        homePage.returnToHomePage();
        homePage.clickHeroMyBookings();
        assertThat(page).hasURL(Pattern.compile(".*/bookings/?$"));
        assertThat(page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName("My Bookings"))).isVisible();
    }

    @Test(groups = {"smoke"}, description = "Verify featured event links and open an event from its card")
    public void featuredEventCardOpensTheLinkedEvent() {
        signIn();

        HomePage homePage = new HomePage(page);
        homePage.verifyHomePageDisplayed();
        homePage.verifyFeaturedEventsDisplayed();
        homePage.verifyFirstFeaturedCardLinksAreConsistent();

        String expectedEventTitle = homePage.getFirstFeaturedEventTitle();
        homePage.openFirstFeaturedEvent();

        assertThat(page).hasURL(Pattern.compile(".*/events/\\d+/?$"));
        assertThat(page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING,
                new com.microsoft.playwright.Page.GetByRoleOptions()
                        .setName(expectedEventTitle))).isVisible();
        assertThat(page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName("Book Tickets"))).isVisible();
    }

    private void signIn() {
        new LoginPage(page, baseUrl).LoginToApplication();
    }

    private void verifyEventsPage() {
        assertThat(page).hasURL(Pattern.compile(".*/events/?$"));
        assertThat(page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName("Upcoming Events"))).isVisible();
    }
}
