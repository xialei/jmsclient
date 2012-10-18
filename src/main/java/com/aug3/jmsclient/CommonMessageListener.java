package com.aug3.jmsclient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

public abstract class CommonMessageListener implements Runnable, MessageListener {

	private final static Logger log = Logger.getLogger(CommonMessageListener.class);

	private final static ExecutorService executorService = Executors.newCachedThreadPool();

	private CommonMessage message;

	@Override
	public void onMessage(Message message) {

		if (message != null) {
			if (message instanceof ObjectMessage) {
				try {
					this.message = (CommonMessage) ((ObjectMessage) message).getObject();
					executorService.submit(this);
				} catch (JMSException e) {
					handleJMSException(e);
				}
			}
		}

	}

	@Override
	public void run() {

		processMessage(message);

	}

	/**
	 * TODO: overwrite this method to implement your own business logic.
	 * 
	 * @param message
	 */
	public abstract void processMessage(CommonMessage message);

	public void handleJMSException(JMSException e) {

		log.error("Error occured in message listener : " + e.getMessage());
	}

	public CommonMessage getMessage() {
		return message;
	}

	public void setMessage(CommonMessage message) {
		this.message = message;
	}

}
