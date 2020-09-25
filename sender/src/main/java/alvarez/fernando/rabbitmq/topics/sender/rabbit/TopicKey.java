package alvarez.fernando.rabbitmq.topics.sender.rabbit;

public enum TopicKey {
	
	DOMAIN_CACHE("domain.cache");
	
	private final String value;
	
	TopicKey(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}