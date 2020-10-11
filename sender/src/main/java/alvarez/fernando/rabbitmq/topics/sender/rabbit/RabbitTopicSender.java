package alvarez.fernando.rabbitmq.topics.sender.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RabbitTopicSender {
	
	private static final Logger logger = LoggerFactory.getLogger(RabbitTopicSender.class);
	
	private final RabbitTemplate rabbitTemplate;
	
	private final TopicExchange topicExchange;
	
	private final ObjectMapper objectMapper;
	
	public RabbitTopicSender(RabbitAdmin rabbitAdmin, RabbitTemplate rabbitTemplate, @Value("${topic}") String topic) {
		this.rabbitTemplate = rabbitTemplate;
		this.topicExchange = new TopicExchange(topic);
		rabbitAdmin.declareExchange(this.topicExchange);
		this.objectMapper = new ObjectMapper();
	}
	
	//Registering it as Bean will allow Spring (or RabbitMQ, idk) to redeclare it when connection is reestablished.
	@Bean
	private TopicExchange getTopicExchange() {
		return topicExchange;
	}
	
	public void send(TopicKey key, @Nullable Object message) throws RabbitTopicSendException {
		try {
			logger.info("Sending message to topic {} and route \"{}\": {}", this.topicExchange.getName(), key.getValue(), message);
			this.rabbitTemplate.convertAndSend(this.topicExchange.getName(), key.getValue(), this.objectMapper.writeValueAsString(message));
		} catch (JsonProcessingException e) {
			throw new RabbitTopicSendException("Failed to parse object to JSON", e);
		}
	}
	
}