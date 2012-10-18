package com.aug3.jmsclient;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import com.aug3.jmsclient.util.ConfigManager;

/**
 * This class is used to create connection for defined MQ.
 * 
 * Currently, we use ActiveMQ as the provider and the connection factory pool is
 * used.
 * 
 * @author xial
 * 
 */
public class MQConnectionCreator {

	private static PooledConnectionFactory pool_conn_factory;

	public static final String DEFAULT_USER = ActiveMQConnection.DEFAULT_USER;

	public static final String DEFAULT_PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

	static {
		initMQConnectionFactory();
	}

	public static Connection createConnection() throws JMSException {
		return pool_conn_factory.createConnection();
	}

	private static synchronized void initMQConnectionFactory() {

		pool_conn_factory = new PooledConnectionFactory();

		String url = ConfigManager.getProperty(ConfigManager.MQ_CONN_URL).trim();

		String user = ConfigManager.getProperty(ConfigManager.MQ_CONN_USER, DEFAULT_USER);

		String passwd = ConfigManager.getProperty(ConfigManager.MQ_CONN_PASSWD, DEFAULT_PASSWORD);

		ActiveMQConnectionFactory activeMQConnFactory = new ActiveMQConnectionFactory(user, passwd, url);

		activeMQConnFactory.setUseAsyncSend(ConfigManager.getBooleanProperty(ConfigManager.MQ_POOL_USE_ASYNCSEND,
				false));

		pool_conn_factory.setConnectionFactory(activeMQConnFactory);

		pool_conn_factory.setMaxConnections(ConfigManager.getIntProperty(ConfigManager.MQ_POOL_CONN_MAX, 10));

		// Sets the maximum number of active sessions per connection
		pool_conn_factory.setMaximumActive(ConfigManager.getIntProperty(ConfigManager.MQ_POOL_ACTIVE_SESSION_MAX,
				200));

		pool_conn_factory.setIdleTimeout(ConfigManager.getIntProperty(ConfigManager.MQ_POOL_IDLETIMEOUT, 30000));

	}

}
