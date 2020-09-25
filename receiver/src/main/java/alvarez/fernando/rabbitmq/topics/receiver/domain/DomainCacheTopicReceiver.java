package alvarez.fernando.rabbitmq.topics.receiver.domain;

import alvarez.fernando.rabbitmq.topics.receiver.rabbit.AbstractRabbitTopicReceiver;
import alvarez.fernando.rabbitmq.topics.receiver.rabbit.RabbitProperties;
import alvarez.fernando.rabbitmq.topics.receiver.rabbit.TopicKey;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

@Component
public class DomainCacheTopicReceiver extends AbstractRabbitTopicReceiver {
	
	private final DomainCache domainCache;
	
	public DomainCacheTopicReceiver(RabbitProperties rabbitProperties, RabbitAdmin rabbitAdmin, DomainCache domainCache) {
		super(rabbitProperties, rabbitAdmin, TopicKey.DOMAIN_CACHE);
		this.domainCache = domainCache;
	}
	
	@Override
	protected void receive(Message message) {
		this.domainCache.updateCache();
	}
	
}