package alvarez.fernando.rabbitmq.topics.receiver.domain;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//Registering Topics as Component (Bean) will allow Spring to redeclare it when connection is reestablished.
@Component
public class DomainCacheTopic extends TopicExchange {
	
	public DomainCacheTopic(@Value("${topic}") String topic) {
		super(topic);
	}
	
}