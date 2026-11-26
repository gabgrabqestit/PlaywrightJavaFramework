package tests;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

public class APITest {
    @Test
    public void e2eApiTest() {
        //Login
        Playwright playwright = Playwright.create();
        APIRequestContext apiRequest = playwright.request().newContext();

        String url = "https://api.eventhub.rahulshettyacademy.com/api/auth/login";

        HashMap<Object, Object> userLoginData = new HashMap<>();
        userLoginData.put("email", "rahulshetty1@yahoo.com");
        userLoginData.put("password", "Magiclife1!");

        RequestOptions requestLoginOptions = RequestOptions.create().setData(userLoginData);

        APIResponse loginResponse = apiRequest.post(url, requestLoginOptions);

        Assert.assertTrue(loginResponse.ok());

        String token = JsonPath.read(loginResponse.text(), "$.token");
        System.out.println(token);

        //Create Event
        url = "https://api.eventhub.rahulshettyacademy.com/api/events";
        String eventTitle = "Playwright API Testing";

        HashMap<Object, Object> createEventPayload = new HashMap<>();
        createEventPayload.put("title", eventTitle);
        createEventPayload.put("description", "api");
        createEventPayload.put("category", "Conference");
        createEventPayload.put("venue", "Main road");
        createEventPayload.put("city", "Bangalore");
        createEventPayload.put("eventDate", "2027-12-16T05:41:00.000Z");
        createEventPayload.put("price", 100);
        createEventPayload.put("totalSeats", 500);

        RequestOptions requestOptions = RequestOptions.create();
        requestOptions.setHeader("Authorization", "Bearer " + token);
        requestOptions.setData(createEventPayload);

        APIResponse createEventResponse = apiRequest.post(url, requestOptions);
        Assert.assertTrue(createEventResponse.ok());
        int eventId = JsonPath.read(createEventResponse.text(), "$.data.id");
        System.out.println(eventId);

        //Retrieve all events
        RequestOptions getEventOptions = RequestOptions.create()
                .setQueryParam("page", "1").setQueryParam("limit", "12")
                .setHeader("Authorization", "Bearer " + token);

        APIResponse getEventsResponse = apiRequest.get(url, getEventOptions);
        Assert.assertTrue(getEventsResponse.ok());

        List<Integer> allEventIds = JsonPath.read(getEventsResponse.text(), "$.data[*].id");

        Assert.assertTrue(allEventIds.contains(eventId));

        //Delete event
        String urlEvent = "https://api.eventhub.rahulshettyacademy.com/api/events/" + eventId;
        APIResponse deleteEventResponse = apiRequest
                .delete(urlEvent, RequestOptions
                        .create()
                        .setHeader("Authorization", "Bearer " + token));

        Assert.assertTrue(deleteEventResponse.ok());

        getEventsResponse = apiRequest.get(url, getEventOptions);
        allEventIds = JsonPath.read(getEventsResponse.text(), "$.data[*].id");
        Assert.assertFalse(allEventIds.contains(eventId));
    }
}
