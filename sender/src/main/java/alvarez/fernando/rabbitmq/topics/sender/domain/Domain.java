package alvarez.fernando.rabbitmq.topics.sender.domain;

import java.util.UUID;

/**
 * <p>Object representing an domain with name.</p>
 * <p>Could be a Person, Product, Client, Car, Animal, Order, etc.</p>
 */
public class Domain {
	
	private final UUID uuid;
	
	private final String name;
	
	public Domain(String name) {
		this.uuid = UUID.randomUUID();
		this.name = name;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public String getName() {
		return name;
	}
	
}