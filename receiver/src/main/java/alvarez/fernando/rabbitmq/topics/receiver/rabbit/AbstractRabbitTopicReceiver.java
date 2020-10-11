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
	
	protected final Binding queueToTopicBinding;
	
	public AbstractRabbitTopicReceiver(TopicExchange topic, TopicKey key, RabbitConfig rabbitConfig) {
		this.key = key;
		this.queue = new Queue(this.createQueueName(rabbitConfig.getApplicationName()), false, true, true);
		
		final RabbitAdmin rabbitAdmin = rabbitConfig.getRabbitAdmin();
		rabbitAdmin.declareExchange(topic);
		rabbitAdmin.declareQueue(queue);
		this.queueToTopicBinding = BindingBuilder.bind(this.queue).to(topic).with(this.key.getValue());
		rabbitAdmin.declareBinding(this.queueToTopicBinding);
		
		final SimpleMessageListenerContainer listener = new SimpleMessageListenerContainer(rabbitConfig.getConnectionFactory());
		listener.addQueues(this.queue);
		listener.setMessageListener(this);
		listener.start();
		
		/*Registering them as Bean will allow Spring (or RabbitMQ, idk) to redeclare them when connection is reestablished.
		  But creating getters annotated with @Bean will create problems when using multiple Receivers, like only one of them successfully redeclaring.
		 */
		rabbitConfig.registerQueueAsBean(this.queue);
		rabbitConfig.registerBindingAsBean(this.queueToTopicBinding);
	}
	
	@Override
	public final void onMessage(Message message) {
		this.logger.info("Message received: {}", message);
		this.receive(message);
	}
	
	protected abstract void receive(Message message);
	
	private String createQueueName(String applicationName) { //You can use whatever logic you would want to here
		try {
			return InetAddress.getLocalHost().getHostName() + "-" + applicationName + "-" + UUID.randomUUID();
		} catch (UnknownHostException e) {
			this.logger.warn("Error creating queue name", e);
			return applicationName + "-" + UUID.randomUUID();
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