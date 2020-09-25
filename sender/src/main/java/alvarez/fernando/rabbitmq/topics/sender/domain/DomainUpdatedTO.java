package alvarez.fernando.rabbitmq.topics.sender.domain;

import java.io.Serializable;
import java.util.UUID;

public class DomainUpdatedTO implements Serializable {
	
	private static final long serialVersionUID = -7284507281339196034L;
	
	private final UUID domainUuid;
	
	private final Action action;
	
	DomainUpdatedTO(UUID domainUuid, Action action) {
		this.domainUuid = domainUuid;
		this.action = action;
	}
	
	public UUID getDomainUuid() {
		return domainUuid;
	}
	
	public Action getAction() {
		return action;
	}
	
	public enum Action {
		
		ADDED,
		DELETED,
		;
		
	}
	
}