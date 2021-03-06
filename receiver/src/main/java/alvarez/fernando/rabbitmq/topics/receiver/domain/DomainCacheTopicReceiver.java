package alvarez.fernando.rabbitmq.topics.receiver.domain;

import alvarez.fernando.rabbitmq.topics.receiver.rabbit.AbstractRabbitTopicReceiver;
import alvarez.fernando.rabbitmq.topics.receiver.rabbit.RabbitConfig;
import alvarez.fernando.rabbitmq.topics.receiver.rabbit.TopicKey;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public class DomainCacheTopicReceiver extends AbstractRabbitTopicReceiver {
	
	private final DomainCache domainCache;
	
	public DomainCacheTopicReceiver(DomainCacheTopic topic, RabbitConfig rabbitConfig, DomainCache domainCache) {
		super(topic, TopicKey.DOMAIN_CACHE, rabbitConfig);
		this.domainCache = domainCache;
	}
	
	@Override
	protected void receive(Message message) {
		this.domainCache.updateCache();
	}
	
}