package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.Constants.OBJECT_PATH;
import static com.mnubo.java.sdk.client.services.EventsSDKServices.EVENT_PATH;
import static com.mnubo.java.sdk.client.services.EventsSDKServices.EVENT_PATH_SEGMENT;
import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mnubo.java.sdk.client.models.Event;
import com.mnubo.java.sdk.client.spi.EventsSDK;

/**
 * Created by mauro on 09/03/16.
 */
public class EventsSDKServicesTest extends AbstractServiceTest {
    private EventsSDK objectClient;

    @Before
    public void eventSetup() {
        objectClient = getClient().getEventClient();
    }

    @Test
    public void postListEventThenOk() {

        List<Event> events = new ArrayList<>();

        events.add(Event.builder().withEventType("type").build());

        String url = getClient().getSdkService().getIngestionBaseUri().path(EVENT_PATH).build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/events",HOST))));

        objectClient.send(events);
    }

    @Test
    public void postListEventListNullThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Event list cannot be null or empty.");
        objectClient.send(null);
    }

    @Test
    public void postListEventListEmptyThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Event list cannot be null or empty.");
        objectClient.send(new ArrayList<Event>());
    }

    @Test
    public void postListEventWithDeviceIdThenOk() {

        List<Event> events = new ArrayList<>();

        events.add(Event.builder().withEventType("type").build());

        String deviceId = "deviceId";

        String url = getClient().getSdkService().getIngestionBaseUri().path(OBJECT_PATH).pathSegment(deviceId, EVENT_PATH_SEGMENT)
                .build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/objects/%s/events",HOST, deviceId))));

        objectClient.send(deviceId, events);
    }

    @Test
    public void postListEventNullThenFail() {

        String deviceId = "deviceId";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Event list cannot be null or empty.");
        objectClient.send(deviceId, null);
    }

    @Test
    public void postListEventEmptyThenFail() {

        String deviceId = "deviceId";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Event list cannot be null or empty.");
        objectClient.send(deviceId, new ArrayList<Event>());
    }

    @Test
    public void postListEventWithDeviceIdNullThenFail() {

        List<Event> events = new ArrayList<>();

        events.add(Event.builder().withEventType("type").build());
        String deviceId = null;

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("device_Id cannot be blank.");
        objectClient.send(deviceId, events);
    }

    @Test
    public void postListEventWithDeviceIdEmptyThenFail() {

        List<Event> events = new ArrayList<>();

        events.add(Event.builder().withEventType("type").build());
        String deviceId = "";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("device_Id cannot be blank.");
        objectClient.send(deviceId, events);
    }
}
