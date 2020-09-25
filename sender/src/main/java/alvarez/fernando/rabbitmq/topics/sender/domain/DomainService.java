package alvarez.fernando.rabbitmq.topics.sender.domain;

import alvarez.fernando.rabbitmq.topics.sender.rabbit.RabbitTopicSendException;
import alvarez.fernando.rabbitmq.topics.sender.rabbit.RabbitTopicSender;
import alvarez.fernando.rabbitmq.topics.sender.rabbit.TopicKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DomainService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DomainService.class);
	
	private final RabbitTopicSender rabbitTopicSender;
	
	//I could use some database but I wanted to keep it simple
	private final Map<UUID, Domain> domainRepository;
	
	public DomainService(RabbitTopicSender rabbitTopicSender) {
		this.rabbitTopicSender = rabbitTopicSender;
		this.domainRepository = Collections.synchronizedMap(new LinkedHashMap<>());
	}
	
	public Domain createDomain(String name) {
		final Domain domain = new Domain(name);
		this.domainRepository.put(domain.getUuid(), domain);
		
		this.notifyDomainUpdate(domain.getUuid(), DomainUpdatedTO.Action.ADDED);
		
		return domain;
	}
	
	public void deleteDomain(UUID domainUuid) throws DomainNotFoundException {
		final Domain deletedDomain = this.domainRepository.remove(domainUuid);
		
		if (deletedDomain == null) {
			throw new DomainNotFoundException(domainUuid);
		}
		
		this.notifyDomainUpdate(domainUuid, DomainUpdatedTO.Action.DELETED);
	}
	
	public Optional<Domain> getDomainByUuid(UUID domainUuid) {
		return Optional.ofNullable(this.domainRepository.get(domainUuid));
	}
	
	public Collection<Domain> getAllDomains() {
		return this.domainRepository.values();
	}
	
	private void notifyDomainUpdate(UUID domainUuid, DomainUpdatedTO.Action action) {
		try {
			rabbitTopicSender.send(TopicKey.DOMAIN_CACHE, new DomainUpdatedTO(domainUuid, action));
		} catch (RabbitTopicSendException e) {
			LOGGER.warn("Error in notifying domain update", e);
		}
	}
	
}