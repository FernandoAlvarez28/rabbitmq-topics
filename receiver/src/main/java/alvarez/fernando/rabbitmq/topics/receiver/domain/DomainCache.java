package alvarez.fernando.rabbitmq.topics.receiver.domain;

import alvarez.fernando.rabbitmq.topics.receiver.client.ClientException;
import alvarez.fernando.rabbitmq.topics.receiver.client.DomainTO;
import alvarez.fernando.rabbitmq.topics.receiver.client.SenderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DomainCache {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DomainCache.class);
	
	private final List<DomainTO> cache;
	
	private final SenderClient senderClient;
	
	public DomainCache(SenderClient senderClient) {
		this.senderClient = senderClient;
		this.cache = Collections.synchronizedList(new ArrayList<>());
	}
	
	public void updateCache() {
		final List<DomainTO> domainTOs;
		try {
			domainTOs = this.senderClient.fetchAllDomains();
		} catch (ClientException e) {
			LOGGER.error("Error during cache update", e);
			return;
		}
		
		this.cache.clear();
		this.cache.addAll(domainTOs);
		
		LOGGER.info("Cache updated: {}", this.cache);
	}

}