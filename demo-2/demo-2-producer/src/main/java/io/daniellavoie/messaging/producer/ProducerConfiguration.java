package io.daniellavoie.messaging.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "producer")
public class ProducerConfiguration {
	private int delayInMillis = 1000;
	private int messageRate = 300;

	public int getDelayInMillis() {
		return delayInMillis;
	}

	public void setDelayInMillis(int delayInMillis) {
		this.delayInMillis = delayInMillis;
	}

	public int getMessageRate() {
		return messageRate;
	}

	public void setMessageRate(int messageRate) {
		this.messageRate = messageRate;
	}
}
