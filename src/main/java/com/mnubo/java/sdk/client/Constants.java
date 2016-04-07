package com.mnubo.java.sdk.client;

public class Constants {
    // Token
    public static final String ACCESS_TOKEN = "access_token";
    public static final String TOKEN_TYPE = "token_type";
    public static final String EXPIRE = "expires_in";
    public static final String SCOPE = "scope";
    public static final String JTI = "jti";

    // Config
    public static final String CONFIG = "client.config.";
    public static final String HOST_NAME = CONFIG + "hostname";
    public static final String PLATFORM_PORT = CONFIG + "platform-port";
    public static final String RESTITUTION_PORT = CONFIG + "restitution-port";
    public static final String AUTHENTICATION_PORT = CONFIG + "autentication-port";
    public static final String HTTP_PROTOCOL = CONFIG + "http-protocol";
    public static final String SECURITY = "client.security.";
    public static final String SECURITY_CONSUMER_KEY = SECURITY + "consumer-key";
    public static final String SECURITY_CONSUMER_SECRET = SECURITY + "consumer-secret";
    public static final String CLIENT = "client.http.client.";
    public static final String CLIENT_DISABLE_REDIRECT_HANDLING = CLIENT + "disable-redirect-handling";
    public static final String CLIENT_DISABLE_AUTOMATIC_RETRIES = CLIENT + "disable-automatic-retries";
    public static final String CLIENT_MAX_CONNECTIONS_PER_ROUTE = CLIENT + "max-connections-per-route";
    public static final String CLIENT_DEFAULT_TIMEOUT = CLIENT + "http.client.default-timeout";
    public static final String CLIENT_CONNECT_TIMEOUT = CLIENT + "http.client.connect-timeout";
    public static final String CLIENT_CONNECTION_REQUEST_TIMEOUT = CLIENT + "connection-request-timeout";
    public static final String CLIENT_SOCKET_TIMEOUT = CLIENT + "http.client.socket-timeout";
    public static final String CLIENT_MAX_TOTAL_CONNECTION = CLIENT + "max-total-connection";
    public static final String CLIENT_BASE_PATH = CLIENT + "base-path";

    public static final int CLIENT_VALIDATE_INACTIVITY_SERVER = 1000;
    public static final int MAX_PORT_VALUE = 65536;

    // Server
    public static final String OBJECT_PATH = "/objects";

    // Security server
    public static final String TOKEN_CONSUMER_SEPARATOR = ":";
    public static final String TOKEN_PATH = "/oauth/token";
    public static final String TOKEN_GRANT_TYPE = "grant_type";
    public static final String TOKEN_GRANT_TYPE_VALUE = "client_credentials";
    public static final String TOKEN_SCOPE = "scope";

    public static final long FLETCHING_TOKEN_MILISEC = 5000;

    public static final String PRINT_OBJECT_NULL = "";

}
