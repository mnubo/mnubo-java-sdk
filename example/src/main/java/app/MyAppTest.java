/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 *
 * Author: marias Date : Jul 23, 2015
 *
 * ---------------------------------------------------------------------------
 */

package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mnubo.java.sdk.client.mapper.SDKMapperUtils;
import com.mnubo.java.sdk.client.models.Event;
import com.mnubo.java.sdk.client.models.Owner;
import com.mnubo.java.sdk.client.models.SmartObject;
import com.mnubo.java.sdk.client.services.MnuboSDKFactory;
import com.mnubo.java.sdk.client.spi.EventsSDK;
import com.mnubo.java.sdk.client.spi.MnuboSDKClient;
import com.mnubo.java.sdk.client.spi.ObjectsSDK;
import com.mnubo.java.sdk.client.spi.OwnersSDK;

public class MyAppTest {
    private static String CONFIG_FILE_NAME = "config.properties";
    private static MnuboSDKClient mnuboClient;
    private static ObjectsSDK mnuboObjectClient;
    private static OwnersSDK mnuboOwnerClient;
    private static EventsSDK mnuboEventClient;

    public static void main(String[] args) {
        if (args[0].equalsIgnoreCase("help")) {
            System.out.println("USAGE:");
            System.out.println("       MyAppTest <Arg1> <Arg2> <Arg3>");
            System.out.println("           <Arg1 mandatory> = File name of the json object to be posted.");
            System.out.println("           <Arg2 optional> = File name of the json eventvalues to be posted.");
            System.out.println("           <Arg3 optional> = File name of the json event file to be posted.");
        }
        else {
            try {
                // ********* taking client handlers *********
                mnuboClient = MnuboSDKFactory.getAdvanceClient(new File(CONFIG_FILE_NAME));
                mnuboObjectClient = mnuboClient.getObjectClient();
                mnuboOwnerClient = mnuboClient.getOwnerClient();
                mnuboEventClient = mnuboClient.getEventClient();

                // ********* posting an owner *******
                createOwner(args[0]);

                if (args.length > 1) {

                    // ********* posting an owner *******
                    createOwner(args[1]);

                    if (args.length > 2) {

                        // ********* posting an object *******
                        createObject(args[2]);

                        if (args.length > 3) {

                            // ********* posting an object *******
                            createObject(args[3]);

                            if (args.length > 4) {

                                // ********* send an event *******
                                sendEvent(args[4]);

                                if (args.length > 6) {
                                    // ********* send an event *******
                                    sendEvent(args[5], args[6]);

                                }
                            }
                        }
                    }
                }
            }
            catch (HttpClientErrorException ex) {
                System.out.println(ex.getResponseBodyAsString());
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("done!!!");
        }
    }

    private static void sendEvent(String objectId, String eventsName) throws IOException {
        // ********* loading event file *******
        String events2BePosted = ReadingFile(eventsName);
        List<Event> events = SDKMapperUtils.readValue(events2BePosted, new TypeReference<List<Event>>() {
        });
        System.out.println("Reading event");

        // ********* send events *******
        mnuboEventClient.send(objectId, events);
        System.out.println("event sent with device id");

    }

    private static void sendEvent(String eventsName) throws IOException {
        // ********* loading object file *******
        String events2BePosted = ReadingFile(eventsName);
        List<Event> events = SDKMapperUtils.readValue(events2BePosted, new TypeReference<List<Event>>() {
        });
        System.out.println("Reading event");

        // ********* send events *******
        mnuboEventClient.send(events);
        System.out.println("event sent");

    }

    private static void createObject(String objectName) throws IOException {
        // ********* loading object file *******
        String object2BePosted = ReadingFile(objectName);
        SmartObject object = SDKMapperUtils.readValue(object2BePosted, SmartObject.class);
        System.out.println("Reading Object");

        // ********* posting an object *******
        mnuboObjectClient.create(object);
        System.out.println("Object created");
    }

    private static void createOwner(String ownerName) throws IOException {
        // ********* loading object file *******
        String owner2BePosted = ReadingFile(ownerName);
        Owner owner = SDKMapperUtils.readValue(owner2BePosted, Owner.class);
        System.out.println("Reading owner");

        // ********* posting an owner *******
        mnuboOwnerClient.create(owner);
        System.out.println("owner created");
    }

    private static String ReadingFile(String fileName) throws IOException {
        String result = "";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            result = sb.toString();
        }
        finally {
            br.close();
        }
        return result;
    }
}
