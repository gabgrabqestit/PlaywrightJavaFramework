package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CreateEventPage {
    Page page;

    private final Locator eventTitleInput;
    private final Locator eventFormTxt;
    private final Locator categoryLbl;
    private final Locator cityInput;
    private final Locator venueInput;
    private final Locator dateTimePicker;
    private final Locator priceInput;
    private final Locator seatsInput;
    private final Locator addEventBtn;
    private final Locator eventCreatedSuccessMessage;

    public CreateEventPage(Page page) {

        this.page = page;
        this.eventTitleInput = page.locator("#event-title-input");
        this.eventFormTxt = page.locator("#admin-event-form textarea");
        this.categoryLbl = page.getByLabel("Category");
        this.cityInput = page.getByLabel("City");
        this.venueInput = page.getByLabel("Venue");
        this.dateTimePicker =  page.getByLabel("Event Date & Time");
        this.priceInput = page.getByLabel("Price ($)");
        this.seatsInput = page.getByLabel("Total Seats");
        this.addEventBtn = page.locator("#add-event-btn");
        this.eventCreatedSuccessMessage = page.getByText("Event created!");

    }

    public void createEvent(String title, String description,
                            String category, String city, String venue,
                            String dateTime, String price, String seats ) {
        //10 seconds - Actions
        eventTitleInput.fill(title, new Locator.FillOptions().setTimeout(10000));
        eventFormTxt.fill(description);
        categoryLbl.selectOption(category);
        cityInput.fill(city);
        venueInput.fill(venue);
        dateTimePicker.fill(dateTime);
        priceInput.fill(price);
        seatsInput.fill(seats);
        addEventBtn.click(new Locator.ClickOptions().setTimeout(12000));

        //Event created! 2-3
        assertThat(eventCreatedSuccessMessage).isVisible(); //5 seconds -Assertions
    }
}
