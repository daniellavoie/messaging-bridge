package io.daniellavoie.messaging.bridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import io.daniellavoie.messaging.bridge.MessageBridgeApplication.Processor;

@SpringBootApplication
@EnableBinding(Processor.class)
public class MessageBridgeApplication {
	public static void main(String[] args) {
		SpringApplication.run(MessageBridgeApplication.class, args);
	}

	private Processor processor;

	public MessageBridgeApplication(Processor processor) {
		this.processor = processor;
	}

	@StreamListener(Processor.INPUT)
	public void handle(byte[] bytes) {
		processor.output().send(MessageBuilder.withPayload(bytes).build());
	}

	public interface Processor {
		String INPUT = "mainframe";
		String OUTPUT = "paas";

		@Input(Processor.INPUT)
		MessageChannel input();

		@Output(Processor.OUTPUT)
		MessageChannel output();
	}
}