package alvarez.fernando.rabbitmq.topics.receiver.client;

import java.util.UUID;

/**
 * Object that represents an Domain received via API.
 */
public class DomainTO {
	
	private UUID uuid;
	
	private String name;
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "DomainTO{" +
				"uuid=" + uuid +
				", name='" + name + '\'' +
				'}';
	}
	
}