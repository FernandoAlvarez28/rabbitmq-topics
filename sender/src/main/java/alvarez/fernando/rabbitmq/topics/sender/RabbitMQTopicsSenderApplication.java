package alvarez.fernando.rabbitmq.topics.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMQTopicsSenderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RabbitMQTopicsSenderApplication.class, args);
	}
	
}