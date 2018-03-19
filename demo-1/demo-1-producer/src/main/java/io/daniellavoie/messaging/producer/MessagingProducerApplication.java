package io.daniellavoie.messaging.producer;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import io.daniellavoie.messaging.producer.MessagingProducerApplication.Source;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@EnableBinding(Source.class)
public class MessagingProducerApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessagingProducerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MessagingProducerApplication.class, args);
	}

	private ProducerConfiguration producerConfiguration;
	private Source source;

	public MessagingProducerApplication(ProducerConfiguration producerConfiguration, Source source) {
		this.producerConfiguration = producerConfiguration;
		this.source = source;
	}

	@PostConstruct
	public void initProducer() {
		// Every X millis
		Flux.interval(Duration.ofMillis(producerConfiguration.getDelayInMillis()))

				// Generate Y events
				.flatMap(i -> Flux.range(0, producerConfiguration.getMessageRate()))

				// Produce a message for the even
				.doOnNext(i -> source.output()
						.send(MessageBuilder.withPayload(new SimpleDateFormat().format(new Date())).build()))

				// Group by pack of Y messages.
				.buffer(producerConfiguration.getMessageRate())

				// Log how many message were produced.
				.doOnNext(messages -> LOGGER.info("Produced " + messages.size() + " messages "))

				.doOnError(ex -> LOGGER.warn("Broker not available yet."))

				.retry()

				.subscribeOn(Schedulers.elastic())

				.subscribe();
	}

	public interface Source {
		String EXCHANGE = "demo1";

		@Output(Source.EXCHANGE)
		MessageChannel output();
	}
}
