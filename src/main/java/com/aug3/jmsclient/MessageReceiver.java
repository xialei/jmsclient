package com.aug3.jmsclient;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class MessageReceiver implements ExceptionListener {

	private final static Logger log = Logger.getLogger(MessageReceiver.class);

	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageConsumer consumer;
	private String selector;

	private String queueName = "DEFAULT_SUBJECT";

	private CommonMessageListener listener = null;

	public MessageReceiver(String destQueueName, CommonMessageListener msgListener) {

		queueName = destQueueName;
		listener = msgListener;

	}

	public MessageReceiver(String destQueueName, String messageSelector, CommonMessageListener msgListener) {

		queueName = destQueueName;
		selector = messageSelector;
		listener = msgListener;

	}

	public void start() {

		log.info("jmsclient tries to start messagelistener!");

		try {
			connection = MQConnectionCreator.createConnection();

			connection.setExceptionListener(this);

			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			destination = session.createQueue(queueName);

			// create a MessageConsumer from the Session to the queue
			if (StringUtils.isBlank(selector))
				consumer = session.createConsumer(destination);
			else
				consumer = session.createConsumer(destination, selector);

			consumer.setMessageListener(listener);

		} catch (JMSException e) {

			handleJMSException(e);
			
			stop();

		}

	}

	public void handleJMSException(JMSException e) {

		// TODO: overwrite this method to implement your own business logic.
		log.error("JMS Exception occurred!" + e.getMessage());
	}

	private void stop() {
		if (connection != null) {
			try {
				if (consumer != null)
					consumer.close();
			} catch (JMSException e) {
				e.printStackTrace();
				consumer = null;
			}

			try {
				if (session != null)
					session.close();
			} catch (JMSException e) {
				e.printStackTrace();
				session = null;
			}

			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
				connection = null;
			}
		}
	}

	@Override
	public void onException(JMSException arg0) {

		log.error("JMSException occured. Shutting down client.");

		stop();
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public CommonMessageListener getListener() {
		return listener;
	}

	public void setListener(CommonMessageListener listener) {
		this.listener = listener;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

}
