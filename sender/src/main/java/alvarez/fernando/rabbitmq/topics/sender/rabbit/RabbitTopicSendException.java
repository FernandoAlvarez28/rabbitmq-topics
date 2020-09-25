package alvarez.fernando.rabbitmq.topics.sender.rabbit;

public class RabbitTopicSendException extends Exception{
	
	RabbitTopicSendException(String message, Throwable cause) {
		super(message, cause);
	}
	
}