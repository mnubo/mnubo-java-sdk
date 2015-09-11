# mnubo Java SDK
This Java SDK client provides you a wrapper to connect your Java application to our mnubo's API.

## Geting started
---
Include the mnubo client in your Java application using:

### Maven
Add the following dependencies into your pom.xml:
```
<dependency>
	<groupId>com.mnubo</groupId>
    <artifactId>java-sdk-client</artifactId>
    <scope>compile</scope>
    <version>1.1.2</version>
</dependency>
```
### Download source code
Download the source code and include it in your Java Application project. All codes are available in **mnubo-java-sdk-client**.

## Configuration
---
A number of parameters must be configured before using the mnubo client. See section usage for more information.

- **Mandatory parameters:**
    - **hostname**.- mnubo's server name, for example: ```rest.sandbox.mnubo.com```.
    - **consumer-key**.- Your unique client identity which is provided by mnubo.
    - **consumer-secret**.- Your secret key which is used in conjunction with the consumer key to access the mnubo server. This key is provided by mnubo.

## Usage
---
Build a mnuboSDKClient instance. This provides all the interfaces required to use this library.

### Getting a "MnuboSDKClient" (client) instance
To get a client instance use **"MnuboSDKFactory" Class**. Note that:
1. You need only one client instance. Internally it provides you multithread support (thread safe) and a pool of connections.
2. All initializations and configuration are done by factory class.

There are two ways to get a client instance.:
- **Basic**, Only 3 mandatory parameters are required – Host, consumer key and consumer secret. Please see the example below:

```
//Configure constants
private final String HOST = "rest.sandbox.mnubo.com";
private final String CONSUMER_KEY = "your consumer key!!!";
private final String CONSUMER_SECRET = "your consumer SECRET!!!";

//getting mnubo client using simple way, taken default values.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );
```

- **Advanced**, this allows configure default parameters using a properties file. Please ask for more information about this option.

#### creating Owners
To create an owner you need to:
1. Request an OwnersSDK interface from the mnubo client instance.
2. Build an owner.

Here is an example:
```
//get mnubo client using basic way.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get Owners client interface
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

Note that the same Owner created above can be deserialized using the a flat Json file as following, "myOwnerFile.json" file:
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

And create it using "SDKMapperUtils" singleton deserializer as:

```
//get mnubo client using basic way.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get Owners client interface
OwnersSDK mnuboOwnersClient = mnuboClient.getOwnerClient();

//read Owner from flat json file
String owner2BePosted = ReadingFile( "myOwnerFile.json" );

//deserialise the json file
Owner owner2Create = SDKMapperUtils.readValue( owner2BePosted , Owner.class );

//create the owner
mnuboOwnersClient.create( owner2Create );
```

#### creating SmartObjects
To create a SmartObject:
1. Request an OwnersSDK interface from the mnubo client instance.
2. Build an object.

```
//get mnubo client using basic way.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get Object client interface
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

or using Json deserializable files:

```
//get mnubo client using basic way.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get Object client interface
ObjectsSDK mnuboObjectClient = mnuboClient.getObjectClient();

//read Object from flat json file
String object2BePosted = ReadingFile( "myObjectFile.json" );

//deserialise the json file
SmartObject object2Create = SDKMapperUtils.readValue( object2BePosted , SmartObject.class );

//create the object
mnuboObjectClient.create( object2Create );
```

Note that this case the flat Json file, "myObjectFile.json", look like:
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

#### Send Events
To send events:
1. Request an OwnersSDK interface from the mnubo client instance.
2. Build an event.

##### Send Events to single object

```
//private String objectID = "mythermostat0301424";

//get mnubo client using basic way.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get Event client interface
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

or using Json deserializable files:

```
//private String objectID = "mythermostat0301424";

//get mnubo client using basic way.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get Event client interface
EventsSDK mnuboEventClient = mnuboClient.getEventClient();

//read event from flat json file
String event2BeSent = ReadingFile( "myEvents.json" );

//deserialise the json file
EventValues event2Send = SDKMapperUtils.readValue( event2BeSent , Events.class );

//send the event
mnuboEventClient.send( objectID, event2Send );
```

Note that in this case the flat Json file, "myEventsByObjectFile.json", look like:
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
##### Send Events to multiples objects

```
//private String objectID = "mythermostat0301424";

//get mnubo client using basic way.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get Event client interface
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

or using Json deserializable files:

```
//get mnubo client using basic way.
MnuboSDKClient mnuboClient = MnuboSDKFactory.getClient( HOST , CONSUMER_KEY , CONSUMER_SECRET );

//get Event client interface
EventsSDK mnuboEventClient = mnuboClient.getEventClient();

//read event from flat json file
String event2Besent = ReadingFile( "myEvents.json" );

//deserialise the json file
EventValues event2Send = SDKMapperUtils.readValue( event2Besent , EventValues.class );

//send the event
mnuboEventClient.send( event2Send );
```

Note that in this case the flat Json file, "myEvents.json", look like:
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