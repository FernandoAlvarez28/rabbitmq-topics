package alvarez.fernando.rabbitmq.topics.receiver.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public abstract class AbstractRabbitTopicReceiver implements MessageListener {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected final TopicKey key;
	
	protected final Queue queue;
	
	public AbstractRabbitTopicReceiver(RabbitProperties rabbitProperties, RabbitAdmin rabbitAdmin, TopicKey key) {
		this.key = key;
		this.queue = new Queue(this.createQueueName(rabbitProperties), false, true, true);
		
		final TopicExchange topicExchange = new TopicExchange(rabbitProperties.getTopic());
		
		rabbitAdmin.declareExchange(topicExchange);
		rabbitAdmin.declareQueue(queue);
		rabbitAdmin.declareBinding(BindingBuilder.bind(this.queue).to(topicExchange).with(this.key.getValue()));
		
		final SimpleMessageListenerContainer listener = new SimpleMessageListenerContainer(rabbitAdmin.getRabbitTemplate().getConnectionFactory());
		listener.addQueues(this.queue);
		listener.setMessageListener(this);
		listener.start();
	}
	
	@Override
	public final void onMessage(Message message) {
		this.logger.info("Message received: {}", message);
		this.receive(message);
	}
	
	protected abstract void receive(Message message);
	
	private String createQueueName(RabbitProperties rabbitProperties) {
		try {
			return InetAddress.getLocalHost().getHostName() + "-" + rabbitProperties.getApplicationName() + "-" + UUID.randomUUID();
		} catch (UnknownHostException e) {
			this.logger.warn("Error creating queue name", e);
			return rabbitProperties.getApplicationName() + "-" + UUID.randomUUID();
		}
	}
	
	@Override
	public String toString() {
		return this.getClass() + "{" +
				"key=" + key +
				", queue=" + queue +
				'}';
	}
	
}