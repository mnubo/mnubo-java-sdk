package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.services.OwnersSDKServices.OWNER_PATH;
import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.mnubo.java.sdk.client.models.Owner;
import com.mnubo.java.sdk.client.spi.OwnersSDK;

/**
 * Created by mauro on 09/03/16.
 */
public class OwnersSDKServicesTest extends AbstractServiceTest {

    private OwnersSDK ownerClient;

    @Before
    public void ownerSetup() {
        ownerClient = getClient().getOwnerClient();
    }

    @Test
    public void createOwnerThenOk() {

        Owner owner = Owner.builder().withUsername("test").build();

        String url = getClient().getSdkService().getIngestionBaseUri().path(OWNER_PATH).build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/owners",HOST))));

        ownerClient.create(owner);
    }

    @Test
    public void createOwnerNullThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Owner body cannot be null.");
        ownerClient.create(null);
    }

    @Test
    public void createOwnerUsernameNullThenFail() {

        Owner owner = Owner.builder().withUsername(null).build();

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("usermame cannot be blank.");
        ownerClient.create(owner);
    }

    @Test
    public void createOwnerUsernameEmptyThenFail() {

        Owner owner = Owner.builder().withUsername("").build();

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("usermame cannot be blank.");
        ownerClient.create(owner);
    }

    @Test
    public void ownerClaimingObjectUsernameNullThenFail() {

        String username = null;
        String deviceId = "deviceId";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("usermame cannot be blank.");
        ownerClient.claim(username, deviceId);
    }

    @Test
    public void ownerClaimingObjectUsernameEmptyThenFail() {

        String username = "";
        String deviceId = "deviceId";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("usermame cannot be blank.");
        ownerClient.claim(username, deviceId);
    }

    @Test
    public void ownerClaimingObjectDeviceNullThenFail() {

        String username = "username";
        String deviceId = null;

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("deviceId cannot be blank.");
        ownerClient.claim(username, deviceId);
    }

    @Test
    public void ownerClaimingObjectDeviceEmptyThenFail() {

        String username = "username";
        String deviceId = "";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("deviceId cannot be blank.");
        ownerClient.claim(username, deviceId);
    }

    @Test
    public void ownerClaimingObjectThenOk() {

        String username = "username";
        String deviceId = "deviceId";

        final String url = getClient().getSdkService().getIngestionBaseUri().path(OWNER_PATH)
                .pathSegment(username, "objects", deviceId, "claim").build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/owners/%s/objects/%s/claim",HOST, username,
                deviceId))));

        ownerClient.claim(username, deviceId);
    }

    @Test
    public void deleteOwnerThenOk() {

        String username = "username";

        final String url = getClient().getSdkService().getIngestionBaseUri().path(OWNER_PATH).pathSegment(username)
                .build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/owners/%s",HOST, username))));

        ownerClient.delete(username);
    }

    @Test
    public void deleteOwnerUsernameNullThenFail() {

        String username = null;

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("usermame cannot be blank.");
        ownerClient.delete(username);
    }

    @Test
    public void deleteOwnerUsernameEmptyThenFail() {

        String username = "";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("usermame cannot be blank.");
        ownerClient.delete(username);
    }

    @Test
    public void updateOwnerThenOk() {

        String username = "username";

        final String url = getClient().getSdkService().getIngestionBaseUri().path(OWNER_PATH).pathSegment(username)
                .build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/owners/%s",HOST, username))));

        ownerClient.update(Owner.builder().build(), username);
    }

    @Test
    public void updateOwnerUsernameNullThenFail() {

        String username = null;

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("usermame cannot be blank.");
        ownerClient.update(Owner.builder().withUsername(null).build(), username);
    }

    @Test
    public void updateOwnerUsernameEmptyThenFail() {

        String username = "";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("usermame cannot be blank.");
        ownerClient.update(Owner.builder().withUsername("").build(), username);
    }

    @Test
    public void updateOwnerOwnerNullThenFail() {

        String username = "username";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Owner body cannot be null.");
        ownerClient.update(null, username);
    }
}
