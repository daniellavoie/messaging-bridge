package io.daniellavoie.messaging.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;

import io.daniellavoie.messaging.consumer.MessagingConsumerApplication.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class MessagingConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MessagingConsumerApplication.class, args);
	}

	@StreamListener(value = Sink.INPUT)
	public void handle(String message) throws InterruptedException {
		
	}

	public interface Sink {
		String INPUT = "paas";

		@Input(Sink.INPUT)
		MessageChannel input();
	}
}
