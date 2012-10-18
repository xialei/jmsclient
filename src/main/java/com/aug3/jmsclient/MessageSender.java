package com.aug3.jmsclient;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.commons.lang.StringUtils;

public class MessageSender {

	private Connection connection;
	private Session session;
	private Queue destinationQueue;
	private MessageProducer producer;

	private String queueName;

	public MessageSender(String destQueueName) {

		this.queueName = destQueueName;
	}

	public void sendMessage(CommonMessage message) {
		try {
			connection = MQConnectionCreator.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destinationQueue = session.createQueue(queueName);
			producer = session.createProducer(destinationQueue);

			ObjectMessage objMessage = session.createObjectMessage();
			objMessage.setObject(message);
			/**
			 * Note: set value to property of Message that will be sent and A
			 * Listener can use match the specified message by queue Selector
			 */
			String messageSelector = message.getMessageSelector();
			if (StringUtils.isNotBlank(messageSelector)) {
				objMessage.setStringProperty(message.getMessageSelectorKey(), messageSelector);
			}
			producer.send(objMessage);

		} catch (JMSException e) {
			handleJMSException(e);
		} finally {
			stop();
		}
	}

	private void handleJMSException(JMSException e) {
		e.printStackTrace();
	}

	private void stop() {
		if (connection != null) {
			try {
				if (producer != null)
					producer.close();
			} catch (JMSException e) {
				e.printStackTrace();
				producer = null;
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

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String destQueueName) {
		this.queueName = destQueueName;
	}

}
