package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.Constants.OBJECT_PATH;
import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.mnubo.java.sdk.client.models.SmartObject;
import com.mnubo.java.sdk.client.spi.ObjectsSDK;

/**
 * Created by mauro on 09/03/16.
 */
public class ObjectsSDKServicesTest extends AbstractServiceTest {
    private ObjectsSDK objectClient;

    @Before
    public void objectSetup() {
        objectClient = getClient().getObjectClient();
    }

    @Test
    public void createObjectThenOk() {

        SmartObject object = SmartObject.builder().withObjectType("type").withDeviceId("device").build();

        String url = getClient().getSdkService().getIngestionBaseUri().path(OBJECT_PATH).build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/objects",HOST))));

        objectClient.create(object);
    }

    @Test
    public void createObjectNullThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Object body cannot be null.");
        objectClient.create(null);
    }

    @Test
    public void createObjectObjectTypeNullThenFail() {

        SmartObject object = SmartObject.builder().withObjectType(null).withDeviceId("deviceId").build();

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("x_object_Type cannot be blank.");
        objectClient.create(object);
    }

    @Test
    public void createObjectObjectTypeEmptyThenFail() {

        SmartObject object = SmartObject.builder().withObjectType("").withDeviceId("deviceId").build();

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("x_object_Type cannot be blank.");
        objectClient.create(object);
    }

    @Test
    public void createObjectDeviceIdNullThenFail() {

        SmartObject object = SmartObject.builder().withObjectType("type").withDeviceId(null).build();

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("x_device_Id cannot be blank.");
        objectClient.create(object);
    }

    @Test
    public void createObjectDeviceIdEmptyThenFail() {

        SmartObject object = SmartObject.builder().withObjectType("type").withDeviceId("").build();

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("x_device_Id cannot be blank.");
        objectClient.create(object);
    }

    @Test
    public void deleteObjectThenOk() {

        String deviceId = "deviceId";

        final String url = getClient().getSdkService().getIngestionBaseUri().path(OBJECT_PATH).pathSegment(deviceId)
                .build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/objects/%s",HOST, deviceId))));

        objectClient.delete(deviceId);
    }

    @Test
    public void deleteObjectDeviceIdNullThenFail() {

        String deviceId = null;

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("device_Id cannot be blank.");
        objectClient.delete(deviceId);
    }

    @Test
    public void deleteObjectDeviceIdEmptyThenFail() {

        String deviceId = "";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("device_Id cannot be blank.");
        objectClient.delete(deviceId);
    }

    @Test
    public void updateObjectThenOk() {

        String deviceId = "deviceId";

        final String url = getClient().getSdkService().getIngestionBaseUri().path(OBJECT_PATH).pathSegment(deviceId)
                .build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/objects/%s",HOST, deviceId))));

        objectClient.update(SmartObject.builder().build(), deviceId);
    }

    @Test
    public void updateObjectDeviceIdNullThenFail() {

        String deviceId = null;

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("device_Id cannot be blank.");
        objectClient.update(SmartObject.builder().withDeviceId(null).build(), deviceId);
    }

    @Test
    public void updateObjectDeviceIdEmptyThenFail() {

        String deviceId = "";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("device_Id cannot be blank.");
        objectClient.update(SmartObject.builder().withDeviceId("").build(), deviceId);
    }

    @Test
    public void updateObjectOwnerNullThenFail() {

        String deviceId = "deviceId";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Object body cannot be null.");
        objectClient.update(null, deviceId);
    }
}
