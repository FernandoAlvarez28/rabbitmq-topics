package alvarez.fernando.rabbitmq.topics.receiver.rabbit;

public enum TopicKey {
	
	DOMAIN_CACHE("domain.cache"),
	ALL("#"),
	;
	
	private final String value;
	
	TopicKey(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}