package alvarez.fernando.rabbitmq.topics.receiver.rabbit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitProperties {
	
	private final String topic;
	
	private final String applicationName;
	
	public RabbitProperties(@Value("${topic}") String topic, @Value("${application.name}") String applicationName) {
		this.topic = topic;
		this.applicationName = applicationName;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	
}