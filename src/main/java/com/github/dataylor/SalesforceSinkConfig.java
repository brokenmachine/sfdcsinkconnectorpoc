package com.github.dataylor;

import com.github.jcustenborder.kafka.connect.utils.config.ValidPattern;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Importance;

import java.util.Map;


public class SalesforceSinkConfig extends AbstractConfig {

    public static final String USERNAME_CONF = "salesforce.username";
    public static final String PASSWORD_CONF = "salesforce.password";
    public static final String PASSWORD_TOKEN_CONF = "salesforce.password.token";
    public static final String CONSUMER_KEY_CONF = "salesforce.consumer.key";
    public static final String CONSUMER_SECRET_CONF = "salesforce.consumer.secret";
    public static final String VERSION_CONF = "salesforce.version";
    public static final String PLATFORM_EVENT_OBJ_API_NAME_CONF = "platform.event.object";
    public static final String CONNECTION_TIMEOUT_CONF = "connection.timeout";

    static final String VERSION_DOC = "The version of the salesforce API to use.";
    static final String USERNAME_DOC = "Salesforce username to connect with.";
    static final String PASSWORD_DOC = "Salesforce password to connect with.";
    static final String PASSWORD_TOKEN_DOC = "The Salesforce security token associated with the username.";
    static final String CONSUMER_KEY_DOC = "The consumer key for the OAuth application.";
    static final String CONSUMER_SECRET_DOC = "The consumer secret for the OAuth application.";
    static final String CONNECTION_TIMEOUT_DOC = "The amount of time to wait while connecting to the Salesforce streaming endpoint.";
    static final String PLATFORM_EVENT_OBJ_API_NAME_DOC = "The Salesforce object to create a topic for.";

    public SalesforceSinkConfig(ConfigDef config, Map<String, ?> parsedConfig) {
        super(config, parsedConfig);
    }

    public SalesforceSinkConfig(Map<String, ?> parsedConfig) {
        this(conf(), parsedConfig);
    }

    public static ConfigDef conf() {
        return new ConfigDef()
                .define(USERNAME_CONF, Type.STRING, Importance.HIGH, USERNAME_DOC)
                .define(PASSWORD_CONF, Type.PASSWORD, Importance.HIGH, PASSWORD_DOC)
                .define(PASSWORD_TOKEN_CONF, Type.PASSWORD, Importance.HIGH, PASSWORD_TOKEN_DOC)
                .define(CONSUMER_KEY_CONF, Type.STRING, Importance.HIGH, CONSUMER_KEY_DOC)
                .define(CONSUMER_SECRET_CONF, Type.PASSWORD, Importance.HIGH, CONSUMER_SECRET_DOC)
                .define(PLATFORM_EVENT_OBJ_API_NAME_CONF, Type.STRING, Importance.HIGH, PLATFORM_EVENT_OBJ_API_NAME_DOC)
                .define(CONNECTION_TIMEOUT_CONF, Type.LONG, 30000L, Importance.LOW, CONNECTION_TIMEOUT_DOC)
                .define(VERSION_CONF, Type.STRING, "latest", ValidPattern.of("^(latest|[\\d\\.]+)$"), Importance.LOW, VERSION_DOC);
    }

    public String username() {
        return this.getString(USERNAME_CONF);
    }

    public String password() {
        return this.getPassword(PASSWORD_CONF).value();
    }

    public String passwordToken() {
        return this.getPassword(PASSWORD_TOKEN_CONF).value();
    }

    public String consumerKey() {
        return this.getString(CONSUMER_KEY_CONF);
    }

    public String consumerSecret() {
        return this.getPassword(CONSUMER_SECRET_CONF).value();
    }

    public String salesForceObject() {
        return this.getString(PLATFORM_EVENT_OBJ_API_NAME_CONF);
    }

    public long connectTimeout() {
        return this.getLong(CONNECTION_TIMEOUT_CONF);
    }

    public String version() {
        return this.getString(VERSION_CONF);
    }
}
