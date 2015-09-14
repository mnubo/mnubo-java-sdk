# mnubo Java SDK
To connect your Java application to our API use the mnubo Java SDK.

## Getting started
---
Include the mnubo client in your Java application using:

### Maven
Add the following into your pom.xml:
```
<dependency>
    <groupId>com.mnubo</groupId>
    <artifactId>java-sdk-client</artifactId>
    <scope>compile</scope>
    <version>1.1.2</version>
</dependency>
```
### Download source code
Download the source code and include it in your Java Application project. All codes are available in the **mnubo-java-sdk-client**.

## Usage
---

### Configuration
The following parameters must be configured before using the mnubo client:

    - **hostname**.- mnubo's server name, for example: ```rest.sandbox.mnubo.com```.
    - **consumer-key**.- Your unique client identity which is provided by mnubo.
    - **consumer-secret**.- Your secret key which is used in conjunction with the consumer key to access the mnubo server. This key is provided by mnubo.

### Getting a "MnuboSDKClient" (client) instance
To get a client instance use the **"MnuboSDKFactory" Class**. Note that you only need one client instance. We provide multithreading support and a pool of connection.

There are two ways to obtain a client instance:

- **Basic**
Using the basic method three mandatory parameters are required – Host, consumer key and consumer secret. Please see the example below:


```
//Example:
//Configure constants
private final String HOST = "rest.sandbox.mnubo.com";
private final String CONSUMER_KEY = "your consumer key!!!";
private final String CONSUMER_SECRET = "your consumer SECRET!!!";

//Obtain a client instance using default values.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );
```

- **Advanced**
Using this method you configure mandatory and optional parameters in a properties file. If you wish to use this method please contact mnubo.

#### Creating Owners
To create an owner you need to:
1. Request an OwnersSDK interface using the mnubo client instance.
2. Build an owner.

This example describes how to create an Owner:
```
//Request a mnubo client using the basic method.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get an Owners client interface
OwnersSDK mnuboOwnersClient = mnuboClient.getOwnerClient();

//build the owner
Owner Owner2Create = Owner.builder()
                          .withUsername("john.smith@mycompany.com")
                          .withPassword("dud7#%^ddd_J")
                          .withRegistrationDate(DateTime.parse("2015-01-01T00:00:00+04:00"))
                          .withAddedAttribute("age", 35)
                          .withAddedAttribute("gender", "male")
                          .withAddedAttribute("height", 1.80)
                          .build();

//create the owner
mnuboOwnersClient.create( Owner2Create );
```

You can also create an owner using a JSON file:

```
//get a mnubo client using the basic method.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get an Owners client interface
OwnersSDK mnuboOwnersClient = mnuboClient.getOwnerClient();

//read the Owner from the JSON file
String owner2BePosted = ReadingFile( "myOwnerFile.json" );

//deserialise the JSON file
Owner owner2Create = SDKMapperUtils.readValue( owner2BePosted , Owner.class );

//create the owner
mnuboOwnersClient.create( owner2Create );
```

The JSON file "myOwnerFile.json" is as follows:

```
{
  "username":"john.smith@mycompany.com",
  "x_password":"dud7#%^ddd_J",
  "x_registration_date":"2015-01-01T00:00:00+04:00",
  "age":35,
  "gender":"male",
  "height":1.80
}
```

#### Creating SmartObjects
To create a SmartObject:
1. Request an ObjectSDK interface from the mnubo client instance.
2. Build an object.

```
//get a mnubo client using the basic method.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get an Object client interface
ObjectsSDK mnuboObjectClient = mnuboClient.getObjectClient();

//build the owner
SmartObject object2Create = SmartObject.builder()
                                       .withDeviceId("connect_alpha.6hv135nw00393.1234567")
                                       .withObjectType("gateway")
                                       .withOwner("john.smith@mycompany.com")
                                       .withRegistrationDate(DateTime.now())
                                       .withAddedAttribute("partnerid", "connect_alpha")
                                       .withAddedAttribute("business_line", "connect")
                                       .withAddedAttribute("siteid", "6hv135nw00393")
                                       .withAddedAttribute("site_description", "My connected House")
                                       .build();

//create the object
mnuboObjectClient.create( object2Create );
```

You can also create an object using a JSON file

```
//get a mnubo client using the basic method.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get an Object client interface
ObjectsSDK mnuboObjectClient = mnuboClient.getObjectClient();

//read the Object from the JSON file
String object2BePosted = ReadingFile( "myObjectFile.json" );

//deserialise the JSON file
SmartObject object2Create = SDKMapperUtils.readValue( object2BePosted , SmartObject.class );

//create the object
mnuboObjectClient.create( object2Create );
```

The JSON file "myObjectFile.json" is as follows:

```
{
    "x_device_id" : "connect_alpha.6hv135nw00393.1234567",
    "x_object_type" : "gateway",
    "x_registration_date":"2015-01-27T08:01:01.000Z",
    "partnerid" : "connect_alpha",
    "business_line" : "connect",
    "siteid" : "6hv135nw00393",
    "site_description" : "My connected House",
    "x_owner":
    {
           "username" : "john.smith@mycompany.com"
    }
}
```

#### Sending Events
To send events:
1. Request an OwnersSDK interface from the mnubo client instance.
2. Build an event.

##### To send multiple events to a single object:

```
//private String objectID = "mythermostat0301424";

//get a mnubo client using the basic method.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get an Event client interface
EventsSDK mnuboEventClient = mnuboClient.getEventClient();

//build the events
List<Event> event2Send = new ArrayList<Event>();
Event event1 = Event.builder()
                    .withEventType("thermostat_temperature")
                    .withEventID(UUID.fromString("11111111-2222-3333-4444-555555555555"))
                    .withTimestamp(DateTime.now())
                    .withAddedTimeseries("temperature", 20)
                    .withAddedTimeseries("errorcode", "")
                    .withAddedTimeseries("varname", "temperature")
                    .build();
event2Send.add(event1);
Event event2 = Event.builder()
                    .withEventType("thermostat_temperature")
                    .withEventID(UUID.fromString("11111111-2222-3333-6666-555555555555"))
                    .withTimestamp(DateTime.now())
                    .withAddedTimeseries("temperature", 22)
                    .withAddedTimeseries("cause_type", "normal")
                    .withAddedTimeseries("varname", "temperature")
                    .build();
event2Send.add(event2);

//send the event
mnuboEventClient.send( objectID, event2Send );
```

You can also send multiple events to a single object using a JSON file:

```
//private String objectID = "mythermostat0301424";

//get a mnubo client using the basic method.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get an Event client interface
EventsSDK mnuboEventClient = mnuboClient.getEventClient();

//read the event from the JSON file
String event2BeSent = ReadingFile( "myEvents.json" );

//deserialise the JSON file
EventValues event2Send = SDKMapperUtils.readValue( event2BeSent , Events.class );

//send the event
mnuboEventClient.send( objectID, event2Send );
```

The JSON file " myEventsByObjectFile.json " is as follows:

```
[
    {
        "x_event_type" : "thermostat_temperature",
        "event_id": "11111111-2222-3333-4444-555555555555",
        "thermostat_temperature": 20,
        "errorcode": "",
        "varname": "temperature",
    },
    {
        "x_event_type" : "thermostat_temperature",
        "event_id": "11111111-2222-3333-6666-555555555555",
        "thermostat_temperature": 22,
        "cause_type": "normal",
        "varname": "temperature",
    }
]
```
##### To send multiple events to multiple objects:

```
//private String objectID = "mythermostat0301424";

//get a mnubo client using the basic method.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get an Event client interface
EventsSDK mnuboEventClient = mnuboClient.getEventClient();

//build the events
List<Event> event2Send = new ArrayList<Event>();
Event event1 = Event.builder()
                    .withEventType("thermostat_temperature")
                    .withSmartObject("connect_alpha.6hv135nw00393.81")
                    .withEventID(UUID.fromString("11111111-2222-3333-4444-555555555555"))
                    .withTimestamp(DateTime.now())
                    .withAddedTimeseries("temperature", 20)
                    .withAddedTimeseries("errorcode", "")
                    .withAddedTimeseries("varname", "temperature")
                    .build();
event2Send.add(event1);
Event event2 = Event.builder()
                    .withEventType("light_dimmer.internal")
                    .withSmartObject("connect_alpha.6hv135nw00393.82")
                    .withEventID(UUID.fromString("11111111-2222-3333-6666-555555555555"))
                    .withTimestamp(DateTime.now())
                    .withAddedTimeseries("light_dimmer", 0)
                    .withAddedTimeseries("cause_type", "internal")
                    .withAddedTimeseries("varname", "level")
                    .withAddedTimeseries("varfunction", "light-dimmer")
                    .build();
event2Send.add(event2);
Event event3 = Event.builder()
                    .withEventType("mask_masked.internal")
                    .withSmartObject("connect_alpha.6hv135nw00393.83")
                    .withEventID(UUID.fromString("11111111-2222-1111-6666-555555555555"))
                    .withTimestamp(DateTime.now())
                    .withAddedTimeseries("mask_masked", "Not Masked")
                    .withAddedTimeseries("cause_type", "internal")
                    .withAddedTimeseries("varname", "mask-state")
                    .build();
event2Send.add(event3);

//send the event
mnuboEventClient.send( event2Send );
```

You can also send multiple events to multiple objects using a JSON file:

```
//get a mnubo client using the basic method.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get an Event client interface
EventsSDK mnuboEventClient = mnuboClient.getEventClient();

//read event from the JSON file
String event2Besent = ReadingFile( "myEvents.json" );

//deserialise the JSON file
EventValues event2Send = SDKMapperUtils.readValue( event2Besent , EventValues.class );

//send the event
mnuboEventClient.send( event2Send );
```

The JSON file "myEvents.json" is as follows:

```
[
    {
        "x_object":
        {
            "x_device_id" :"connect_alpha.6hv135nw00393.81"
        },
        "x_event_type": "thermostat_temperature",
        "event_id": "11111111-2222-3333-4444-555555555555",
        "thermostat_temperature": 20,
        "cause_type": null,
        "errorcode": "",
        "varname": "temperature",
    },
    {
        "x_object":
        {
            "x_device_id" :"connect_alpha.6hv135nw00393.82"
        },
        "x_event_type": "light_dimmer.internal",
        "event_id": "11111111-2222-3333-6666-555555555555",
        "light_dimmer": 0,
        "cause_type": "internal",
        "errorcode": null,
        "varname": "level",
        "varfunction": "light-dimmer",
    },
    {
        "x_object":
        {
            "x_device_id" :"connect_alpha.6hv135nw00393.83"
        },
        "x_event_type": "mask_masked.internal",
        "x_timestamp": "2014-10-30T21:46:10+00:00",
        "event_id": "11111111-2222-1111-6666-555555555555",
        "mask_masked": "Not Masked",
        "cause_type": "internal",
        "errorcode": null,
        "varname": "mask-state",
    }
]
```