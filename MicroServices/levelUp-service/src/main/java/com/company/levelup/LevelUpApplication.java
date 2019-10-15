package com.company.levelup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class LevelUpApplication {

	public static final String TOPIC_EXCHANGE_NAME = "levelup-exchange";
	public static final String QUEUE_NAME = "levelup-queue";
	public static final String ROUTING_KEY = "levelup.create.#";

	// indicates whether the queue should be durable between restarts of the RabbitMQ broker
	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, false);
	}

	// Will instantiate the Topic Exchange Object
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	// Binding the queue and exchange together with the routing key
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

	// Jackson converter we will need to convert CommentViewModel object to json
	// Producer(Post Service) calls convertAndSend --> Consumer(CommentService) receives as Json for us
	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
		return new Jackson2JsonMessageConverter(objectMapper);
	}

	public static void main(String[] args) {
		SpringApplication.run(LevelUpApplication.class, args);
	}

}
