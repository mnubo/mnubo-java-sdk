package com.mnubo.java.sdk.client.services;

import static org.mockito.Mockito.mock;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.web.client.RestTemplate;

import com.mnubo.java.sdk.client.config.MnuboSDKConfig;

/**
 * Created by mauro on 09/03/16.
 */
public abstract class AbstractServiceTest {

    protected static String HOST = "host";

    protected static RestTemplate restTemplate = mock(RestTemplate.class);
    private static CredentialHandler credentials = mock(CredentialHandler.class);
    private static MnuboSDKConfig config = MnuboSDKConfig.builder().withHostName(HOST).withSecurityConsumerKey("CK")
                                                         .withSecurityConsumerSecret("SC").build();
    protected static MnuboSDKClientImpl client = new MnuboSDKClientImpl(config, restTemplate, credentials);

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    public static MnuboSDKClientImpl getClient() {
        return client;
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public static CredentialHandler getCredentials() {
        return credentials;
    }

    public static MnuboSDKConfig getConfig() {
        return config;
    }
}
