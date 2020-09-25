package alvarez.fernando.rabbitmq.topics.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMQTopicsReceiverApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RabbitMQTopicsReceiverApplication.class, args);
	}
	
}