package alvarez.fernando.rabbitmq.topics.receiver.domain;

import alvarez.fernando.rabbitmq.topics.receiver.rabbit.AbstractRabbitTopicReceiver;
import alvarez.fernando.rabbitmq.topics.receiver.rabbit.RabbitConfig;
import alvarez.fernando.rabbitmq.topics.receiver.rabbit.TopicKey;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * Class with the only purpuse to exemplify and test multiple TopicReceivers, together with {@link DomainCacheTopicReceiver}.
 */
@Component
public class DomainAnyRouteTopicReceiver extends AbstractRabbitTopicReceiver {
	
	public DomainAnyRouteTopicReceiver(DomainCacheTopic topic, RabbitConfig rabbitConfig) {
		super(topic, TopicKey.ALL, rabbitConfig);
	}
	
	@Override
	protected void receive(Message message) {
		//not implemented on purpose
	}
	
}