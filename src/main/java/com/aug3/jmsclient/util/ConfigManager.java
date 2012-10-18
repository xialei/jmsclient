package com.aug3.jmsclient.util;

import com.aug3.sys.props.ConfigProperties;

/**
 * This class loads and parses the configuration information for MQ.
 * Configuration info is fetched from the properties resource file
 * <code>mq.properties</code> which should be placed in the classpath.
 * 
 * 
 * @author xial
 */
public class ConfigManager {

	// configuration properties
	public static final String MQ_CONN_URL = "activemq_url";
	public static final String MQ_CONN_USER = "activemq.connection.user";
	public static final String MQ_CONN_PASSWD = "activemq.connection.passwd";

	public static final String MQ_POOL_CONN_MAX = "activemq_maxConnections";
	public static final String MQ_POOL_ACTIVE_SESSION_MAX = "activemq_connection_sessions";
	public static final String MQ_POOL_IDLETIMEOUT = "activemq_idleTimeout";
	public static final String MQ_POOL_USE_ASYNCSEND = "activemq_useAsyncSend";
	public static final String MQ_RECEIVE_TIMEOUT = "activemq_receive_timeout";
	
	private static ConfigProperties props = new ConfigProperties("/common-jms.properties");

	public static String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	public static int getIntProperty(String key, int defaultValue) {
		return props.getIntProperty(key, defaultValue);
	}

	public static int getIntProperty(String key) {
		return props.getIntProperty(key);
	}

	public static boolean getBooleanProperty(String key) {
		return props.getBooleanProperty(key);
	}

	public static boolean getBooleanProperty(String key, boolean defaultValue) {
		return props.getBooleanProperty(key, defaultValue);
	}

}
