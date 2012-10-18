package com.aug3.jmsclient;

import java.io.Serializable;

public class CommonMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Note: set value to property of Message that will be sent and A Listener
	 * can use match the specified message by queue Selector
	 */
	private String messageSelector;

	private String messageSelectorKey = "msgselector";

	public String getMessageSelector() {
		return messageSelector;
	}

	public void setMessageSelector(String messageSelector) {
		this.messageSelector = messageSelector;
	}

	public String getMessageSelectorKey() {
		return messageSelectorKey;
	}

	public void setMessageSelectorKey(String messageSelectorKey) {
		this.messageSelectorKey = messageSelectorKey;
	}

}
