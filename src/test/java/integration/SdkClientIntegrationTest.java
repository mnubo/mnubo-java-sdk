package integration;

import static java.lang.String.format;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

import com.mnubo.java.sdk.client.models.DataSet;
import com.mnubo.java.sdk.client.models.Event;
import com.mnubo.java.sdk.client.models.Owner;
import com.mnubo.java.sdk.client.models.SearchResultSet;
import com.mnubo.java.sdk.client.models.SmartObject;
import com.mnubo.java.sdk.client.services.MnuboSDKFactory;
import com.mnubo.java.sdk.client.spi.MnuboSDKClient;

/*
CHANGE_ME: YOU MUST REMOVE OR COMMENT OUT THE 'IGNORE' ANNOTATION TO RUN YOUR INTEGRATION TEST.
 */
@Ignore
@FixMethodOrder(NAME_ASCENDING)
public class SdkClientIntegrationTest {
    private final static Log log = LogFactory.getLog(SdkClientIntegrationTest.class);

    private final String HOSTNAME = "rest.sandbox.mnubo.com";

    /*
    CHANGE_ME: YOU MUST PUT HERE YOUR CONSUMER KEY, DON'T FORGET TO ASK MNUBO FOR THIS.
     */
    private final String CONSUMER_KEY = " < CHANGE_ME: PUT HERE YOUR CONSUMER KEY > ";

    /*
    CHANGE_ME: YOU MUST PUT HERE YOUR CONSUMER SECRET, DON'T FORGET TO ASK MNUBO FOR THIS.
     */
    private final String CONSUMER_SECRET = " < CHANGE_ME: PUT HERE YOUR CONSUMER SECRET > ";

    private final String DEVICE_ID = "MyDeviceTest01";

    private final String OWNER_USERNAME = "MyOwnerTest01";

    private final String DEVICE_ID_WITH_OWNER_LINK = "MyDeviceTest02";

    private final String OWNER_USERNAME_WITH_OWNER_LINK = "MyOwnerTest02";

    private final String EVENT_TYPE = "EventType";

    private final String OBJECT_TYPE = "ObjectType";

    private final String OWNER_PASSWORD = "myPassword";

    private final Map<String, Object> OWNER_ATTRIBUTES = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER ATTRIBUTES DESIRED IN OWNER POST REQUESTS. REMEMBER, EACH ATTRIBUTE NAME
        HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
        put(" < CHANGE_ME: PUT HERE THE ATTRIBUTE'S NAME > ", " < CHANGE_ME: PUT HERE ITS VALUE > ");
    }};

    private final Map<String, Object> OBJECT_ATTRIBUTES = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER ATTRIBUTES DESIRED IN OBJECT POST REQUESTS. REMEMBER, EACH ATTRIBUTE NAME
        HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
        put(" < CHANGE_ME: PUT HERE THE ATTRIBUTE'S NAME > ", " < CHANGE_ME: PUT HERE ITS VALUE > ");
    }};

    private final Map<String, Object> OWNER_ATTRIBUTES_2_UPDATE = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER ATTRIBUTES DESIRED IN OWNER PUT REQUESTS. REMEMBER, EACH ATTRIBUTE NAME
        HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
        put(" < CHANGE_ME: PUT HERE THE ATTRIBUTE'S NAME > ", " < CHANGE_ME: PUT HERE ITS VALUE > ");
    }};

    private final Map<String, Object> OBJECT_ATTRIBUTES_2_UPDATE = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER ATTRIBUTES DESIRED IN OBJECT PUT REQUESTS. REMEMBER, EACH ATTRIBUTE NAME
        HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
        put(" < CHANGE_ME: PUT HERE THE ATTRIBUTE'S NAME > ", " < CHANGE_ME: PUT HERE ITS VALUE > ");
    }};

    private final Map<String, Object> TIMESERIES_2_POST = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE TIMESERIES DESIRED IN POST EVENTS. REMEMBER, EACH TIMESERIE NAME HAS TO EXIST
        IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
        put(" < CHANGE_ME: PUT HERE THE TIMESERIE'S NAME > ", " < CHANGE_ME: PUT HERE ITS VALUE > ");
    }};

    private final Map<String, Object> EXTRA_TIMESERIES_2_POST = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE EXTRAS TIMESERIES DESIRED IN A SECOND POST EVENTS. THIS CAN BE THE SAME TO
        'TIMESERIES_2_POST' OR NOT. REMEMBER, EACH TIMESERIE NAME HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS
        VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
         */
        put(" < CHANGE_ME: PUT HERE THE ATTRIBUTE'S NAME > ", " < CHANGE_ME: PUT HERE ITS VALUE > ");
    }};
    
    private final String QUERY = 
            /*
            CHANGE_ME: YOU MUST MODIFY HERE THE QUERY SENT TO THE SEARCH API(S)
            Example: "{ \"from\": \"event\", \"select\": [ {\"value\": \"speed\"} ] }"
            */
            "{ CHANGE_ME }";

    MnuboSDKClient client = MnuboSDKFactory.getClient(HOSTNAME, CONSUMER_KEY, CONSUMER_SECRET);

    @Test
    public void T001_CreateOwner() {
        try {

            //Owner to be created
            Owner owner = Owner
                    .builder()
                    .withUsername(OWNER_USERNAME)
                    .withPassword(OWNER_PASSWORD)
                    .withAttributes(OWNER_ATTRIBUTES)
                    .build();

            //Posting Owner
            client.getOwnerClient().create(owner);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T005_UpdateOwner() {
        try {

            //Owner to be updated
            Owner owner = Owner
                    .builder()
                    .withAttributes(OWNER_ATTRIBUTES_2_UPDATE)
                    .build();

            //Putting Owner
            client.getOwnerClient().update(owner, OWNER_USERNAME);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T010_DeleteOwner() {
        try {

            //Owner to be created & deleted
            String userName = "createMeThenDeleteMe";

            Owner owner = Owner
                    .builder()
                    .withUsername(userName)
                    .withPassword(OWNER_PASSWORD)
                    .build();

            //Posting Owner
            client.getOwnerClient().create(owner);

            //deleting Owner
            client.getOwnerClient().delete(userName);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T015_CreateObject() {
        try {

            //Object to be created
            SmartObject object = SmartObject
                    .builder()
                    .withDeviceId(DEVICE_ID)
                    .withObjectType(OBJECT_TYPE)
                    .withAttributes(OBJECT_ATTRIBUTES)
                    .build();

            //Posting Object
            client.getObjectClient().create(object);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T020_CreateObjectWithOwner() {
        try {

            //Owner to be linked
            Owner owner = Owner
                    .builder()
                    .withUsername(OWNER_USERNAME_WITH_OWNER_LINK)
                    .withPassword(OWNER_PASSWORD)
                    .build();

            //Posting Owner to be linked, this must be posted before to link it to an object.
            client.getOwnerClient().create(owner);

            //Object to be created,
            SmartObject object = SmartObject
                    .builder()
                    .withDeviceId(DEVICE_ID_WITH_OWNER_LINK)
                    .withObjectType(OBJECT_TYPE)
                    .withAttributes(OBJECT_ATTRIBUTES)
                    .withOwner(OWNER_USERNAME_WITH_OWNER_LINK)
                    .build();

            //Posting Object
            client.getObjectClient().create(object);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T025_UpdateObject() {
        try {

            //Object to be updated
            SmartObject object = SmartObject
                    .builder()
                    .withAttributes(OBJECT_ATTRIBUTES_2_UPDATE)
                    .build();

            //Putting Object
            client.getObjectClient().update(object, DEVICE_ID);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T030_DeleteObject() {
        try {

            //Object to be created & deleted
            String deviceId = "createMeThenDeleteMe";

            SmartObject object = SmartObject
                    .builder()
                    .withDeviceId(deviceId)
                    .withObjectType(OBJECT_TYPE)
                    .build();

            //Posting Owner
            client.getObjectClient().create(object);

            //deleting Owner
            client.getObjectClient().delete(deviceId);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T035_OwnerClaimingObject() {
        try {


            //claiming Owner. Both Object and Owner must have been created before.
            client.getOwnerClient().claim(OWNER_USERNAME, DEVICE_ID);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T040_PostingOneEvent() {
        try {

            //event to be posted
            Event event = Event
                    .builder()
                    .withEventType(EVENT_TYPE)
                    .withSmartObject(DEVICE_ID)
                    .withTimeseries(TIMESERIES_2_POST)
                    .build();

            //Posting event
            client.getEventClient().send(Arrays.asList(event));

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T045_PostingEvents() {
        try {

            //events to be posted
            Event event1 = Event
                    .builder()
                    .withEventType(EVENT_TYPE)
                    .withSmartObject(DEVICE_ID)
                    .withTimeseries(TIMESERIES_2_POST)
                    .build();

            Event event2 = Event
                    .builder()
                    .withEventType(EVENT_TYPE)
                    .withSmartObject(DEVICE_ID)
                    .withTimeseries(EXTRA_TIMESERIES_2_POST)
                    .build();

            List<Event> events = new ArrayList<Event>();
            events.add(event1);
            events.add(event2);

            //Posting events
            client.getEventClient().send(events);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T050_PostingEventsWithDeviceId() {
        try {

            //events to be posted
            Event event1 = Event
                    .builder()
                    .withEventType(EVENT_TYPE)
                    .withTimeseries(TIMESERIES_2_POST)
                    .build();

            Event event2 = Event
                    .builder()
                    .withEventType(EVENT_TYPE)
                    .withTimeseries(EXTRA_TIMESERIES_2_POST)
                    .build();

            List<Event> events = new ArrayList<Event>();
            events.add(event1);
            events.add(event2);

            //Posting events
            client.getEventClient().send(DEVICE_ID, events);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T055_CleanUp() {
        client.getOwnerClient().delete(OWNER_USERNAME);
        client.getOwnerClient().delete(OWNER_USERNAME_WITH_OWNER_LINK);
        client.getObjectClient().delete(DEVICE_ID);
        client.getObjectClient().delete(DEVICE_ID_WITH_OWNER_LINK);
    }
    
    @Test
    public void T060_SearchBasic() {
        try {
            // Perform a Basic Search
            SearchResultSet searchResultSet = client.getSearchClient().search(QUERY);
            
            assertNotNull("Search Result is NULL", searchResultSet);

        }
        catch (HttpClientErrorException ex) {

            log.error(format("Error code: %s, Error Message: %s", ex.getStatusCode(), ex.getResponseBodyAsString()),
                    ex);
            fail();
        }
        catch (Exception ex) {

            log.error(format("Error Message: %s", ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T060_SearchDataset() {
        try {
            // Perform a Basic Search
            List<DataSet> datasets = client.getSearchClient().getDatasets();
            
            assertNotNull(datasets);
            assertTrue("Dataset size = 0", datasets.size() > 0);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }
}
