package pages;

import com.microsoft.playwright.Page;

public class AdminEventsPage {
    Page page;
    private static final String url = "https://eventhub.rahulshettyacademy.com/admin/events";

    public AdminEventsPage(Page page) {
        this.page = page;
    }
    public void navigateToEventsPage() {
        page.navigate(url);
    }

}
