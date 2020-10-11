package alvarez.fernando.rabbitmq.topics.receiver.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

@Configuration
public class RabbitConfig {
	
	private final GenericApplicationContext context;
	
	private final ConnectionFactory connectionFactory;
	
	private final RabbitAdmin rabbitAdmin;
	
	private final String applicationName;
	
	public RabbitConfig(GenericApplicationContext context,
	                    ConnectionFactory connectionFactory,
	                    @Value("${application.name}") String applicationName) {
		this.context = context;
		this.connectionFactory = connectionFactory;
		this.rabbitAdmin = new RabbitAdmin(connectionFactory);
		this.applicationName = applicationName;
	}
	
	public void registerQueueAsBean(Queue queue) {
		this.context.getBeanFactory().registerSingleton("Queue-" + queue.getName(), queue);
	}
	
	public void registerBindingAsBean(Binding binding) {
		this.context.getBeanFactory().registerSingleton("Binding-" + binding.getExchange() + "-" + binding.getRoutingKey() + "-" + binding.getDestination(), binding);
	}
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	
	@Bean
	public RabbitAdmin getRabbitAdmin() {
		return this.rabbitAdmin;
	}
	
	public String getApplicationName() {
		return this.applicationName;
	}
	
}