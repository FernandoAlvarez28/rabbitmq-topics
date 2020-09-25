package alvarez.fernando.rabbitmq.topics.sender.domain;

import java.util.UUID;

public class DomainNotFoundException extends Exception {
	
	DomainNotFoundException(UUID searchedUuid) {
		super("Domain with UUID \"" + searchedUuid + "\" could not be found");
	}
	
}