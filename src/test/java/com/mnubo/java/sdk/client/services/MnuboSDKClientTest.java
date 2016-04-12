package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.Constants.AUTHENTICATION_PORT;
import static com.mnubo.java.sdk.client.Constants.CLIENT_BASE_PATH;
import static com.mnubo.java.sdk.client.Constants.CLIENT_CONNECTION_REQUEST_TIMEOUT;
import static com.mnubo.java.sdk.client.Constants.CLIENT_CONNECT_TIMEOUT;
import static com.mnubo.java.sdk.client.Constants.CLIENT_DEFAULT_TIMEOUT;
import static com.mnubo.java.sdk.client.Constants.CLIENT_DISABLE_AUTOMATIC_RETRIES;
import static com.mnubo.java.sdk.client.Constants.CLIENT_DISABLE_REDIRECT_HANDLING;
import static com.mnubo.java.sdk.client.Constants.CLIENT_MAX_CONNECTIONS_PER_ROUTE;
import static com.mnubo.java.sdk.client.Constants.CLIENT_MAX_TOTAL_CONNECTION;
import static com.mnubo.java.sdk.client.Constants.CLIENT_SOCKET_TIMEOUT;
import static com.mnubo.java.sdk.client.Constants.HOST_NAME;
import static com.mnubo.java.sdk.client.Constants.HTTP_PROTOCOL;
import static com.mnubo.java.sdk.client.Constants.PLATFORM_PORT;
import static com.mnubo.java.sdk.client.Constants.SECURITY_CONSUMER_KEY;
import static com.mnubo.java.sdk.client.Constants.SECURITY_CONSUMER_SECRET;
import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.web.client.RestTemplate;

import com.mnubo.java.sdk.client.config.MnuboSDKConfig;

/**
 * Created by mauro on 09/03/16.
 */
public class MnuboSDKClientTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private MnuboSDKClientImpl client;
    private MnuboSDKConfig config;
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final CredentialHandler credentials = mock(CredentialHandler.class);

    private enum PropertyType {
        TEXT,
        PORT,
        HTTP,
        BOOL,
        INT
    }

    private Map<String, PropertyType> PROPERTY_NAME_LIST = new HashMap<String, PropertyType>() {
        {
            put(HOST_NAME, PropertyType.TEXT);
            put(PLATFORM_PORT, PropertyType.PORT);
            put(AUTHENTICATION_PORT, PropertyType.PORT);
            put(SECURITY_CONSUMER_KEY, PropertyType.TEXT);
            put(SECURITY_CONSUMER_SECRET, PropertyType.TEXT);
            put(HTTP_PROTOCOL, PropertyType.HTTP);
            put(CLIENT_DISABLE_REDIRECT_HANDLING, PropertyType.BOOL);
            put(CLIENT_DISABLE_AUTOMATIC_RETRIES, PropertyType.BOOL);
            put(CLIENT_MAX_CONNECTIONS_PER_ROUTE, PropertyType.INT);
            put(CLIENT_DEFAULT_TIMEOUT, PropertyType.INT);
            put(CLIENT_CONNECT_TIMEOUT, PropertyType.INT);
            put(CLIENT_CONNECTION_REQUEST_TIMEOUT, PropertyType.INT);
            put(CLIENT_SOCKET_TIMEOUT, PropertyType.INT);
            put(CLIENT_MAX_TOTAL_CONNECTION, PropertyType.INT);
            put(CLIENT_BASE_PATH, PropertyType.TEXT);
        }
    };

    @Test
    public void blankHostThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("hostname property cannot be empty or null.");
        MnuboSDKFactory.getClient("", "consumer_key", "cosumer_secret");
    }

    @Test
    public void nullHostThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("hostname property cannot be empty or null.");
        MnuboSDKFactory.getClient(null, "consumer_key", "cosumer_secret");
    }

    @Test
    public void blankCKThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("securityConsumerKey property cannot be empty or null.");
        MnuboSDKFactory.getClient("host", "", "cosumer_secret");
    }

    @Test
    public void nullCKThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("securityConsumerKey property cannot be empty or null.");
        MnuboSDKFactory.getClient("host", null, "cosumer_secret");
    }

    @Test
    public void blankCSThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("securityConsumerSecret property cannot be empty or null.");
        MnuboSDKFactory.getClient("host", "consumer_key", "");
    }

    @Test
    public void nullCSThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("securityConsumerSecret property cannot be empty or null.");
        MnuboSDKFactory.getClient("host", "consumer_key", null);
    }

    @Test
    public void getAdvanceClientWithNullFileThenFail() throws Exception {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("configuration file cannot be null.");
        File file = null;
        MnuboSDKFactory.getAdvanceClient(file);
    }

    @Test
    public void getAdvanceClientWithNullInputStreamThenFail() throws Exception {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("configuration streaming instance cannot be null.");
        InputStream stream = null;
        MnuboSDKFactory.getAdvanceClient(stream);
    }

    @Test
    public void getAdvanceClientWithNullPropertiesThenFail() throws Exception {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Configuration properties instance cannot be null.");
        Properties properties = null;
        MnuboSDKFactory.getAdvanceClient(properties);
    }

    @Test
    public void getAdvanceClientWithWrongPropertyTypeThenFail() throws Exception {

        for (Map.Entry<String, PropertyType> entry : PROPERTY_NAME_LIST.entrySet()) {

            String propertyName = entry.getKey();

            Properties properties = new Properties();
            String errorMessage = null;

            switch (entry.getValue()) {
                case TEXT:
                    properties.setProperty(propertyName, "");
                    errorMessage = format("%s property has to be a valid String.", propertyName);
                    break;
                case PORT:
                    properties.setProperty(propertyName, "120000");
                    errorMessage = format("%s property has to be a valid port.", propertyName);
                    break;
                case INT:
                    properties.setProperty(propertyName, "54g");
                    errorMessage = format("%s property has to be an positive integer.", propertyName);
                    break;
                case HTTP:
                    properties.setProperty(propertyName, "ftp");
                    errorMessage = format("client.config.http-protocol has to be equal to \"http\" or \"https\"");
                    break;
                case BOOL:
                    properties.setProperty(propertyName, "maybe");
                    errorMessage = format("%s property has to be a boolean valid.", propertyName);
                    break;
            }
            try {

                MnuboSDKFactory.getAdvanceClient(properties);

            } catch (IllegalStateException ex) {

                assertThat(ex.getMessage(), is(equalTo(errorMessage)));
            }
        }
    }

    @Test
    public void defaultTokenConfig() {

        String hostname = "host";
        String token = "myToken";
        config = MnuboSDKConfig.builder().withHostName(hostname).withSecurityConsumerKey("CK")
                .withSecurityConsumerSecret("CS").build();

        when(credentials.getAutorizationToken()).thenReturn(token);

        client = new MnuboSDKClientImpl(config, restTemplate, credentials);

        assertThat(client.getSdkService().getAutorizationHeader().toString(),
                is(equalTo(format("{Authorization=[%s]}",token))));
    }

    @Test
    public void defaulIngestiontUrlConfig() {

        String hostname = "host";
        config = MnuboSDKConfig.builder().withHostName(hostname).withSecurityConsumerKey("CK")
                .withSecurityConsumerSecret("CS").build();

        client = new MnuboSDKClientImpl(config, restTemplate, credentials);

        assertThat(client.getSdkService().getIngestionBaseUri().build().toString(),
                is(equalTo(format("https://%s:443/api/v3",hostname))));
    }

    @Test
    public void defaulRestitutiontUrlConfig() {

        String hostname = "host";
        config = MnuboSDKConfig.builder().withHostName(hostname).withSecurityConsumerKey("CK")
                .withSecurityConsumerSecret("CS").build();

        client = new MnuboSDKClientImpl(config, restTemplate, credentials);

        assertThat(client.getSdkService().getRestitutionBaseUri().build().toString(),
                is(equalTo(format("https://%s:443/api/v3", hostname))));
    }

    @Test
    public void customIngestionUrlConfig() {

        String hostname = "host";
        String basePath = "my/base/path";
        String port = "5656";
        String protocol = "http";
        config = MnuboSDKConfig.builder().withHostName(hostname).withHttpBasePath(basePath).withPlatformPort(port)
                .withHttpProtocol(protocol).withSecurityConsumerKey("CK").withSecurityConsumerSecret("CS").build();

        client = new MnuboSDKClientImpl(config, restTemplate, credentials);

        assertThat(client.getSdkService().getIngestionBaseUri().build().toString(),
                is(equalTo(format("%s://%s:%s/%s",protocol, hostname, port, basePath))));
    }

    @Test
    public void customRestitutionUrlConfig() {

        String hostname = "host";
        String basePath = "my/base/path";
        String port = "5657";
        String protocol = "http";
        config = MnuboSDKConfig.builder().withHostName(hostname).withHttpBasePath(basePath).withRestitutionPort(port)
                .withHttpProtocol(protocol).withSecurityConsumerKey("CK").withSecurityConsumerSecret("CS").build();

        client = new MnuboSDKClientImpl(config, restTemplate, credentials);

        assertThat(client.getSdkService().getRestitutionBaseUri().build().toString(),
                is(equalTo(format("%s://%s:%s/%s", protocol, hostname, port, basePath))));
    }
}
