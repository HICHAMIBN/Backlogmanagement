package com.example.backlogmanagement.Backlog.Config;

import org.springframework.amqp.core.Binding;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMQConfig {
	
	@Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
	
	@Bean
	public Queue queue() {
	    return new Queue("userStoryToSprintAssignmentQueue", true);
	}

	@Bean
	public DirectExchange exchange() {
	    return new DirectExchange("userStoryExchange");
	}

	@Bean
	public Binding binding(Queue queue, DirectExchange exchange) {
	    return BindingBuilder.bind(queue).to(exchange).with("userStoryRoutingKey");
	}
	
}
