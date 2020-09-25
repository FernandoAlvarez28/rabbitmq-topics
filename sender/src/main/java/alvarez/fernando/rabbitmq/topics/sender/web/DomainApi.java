package alvarez.fernando.rabbitmq.topics.sender.web;

import alvarez.fernando.rabbitmq.topics.sender.domain.Domain;
import alvarez.fernando.rabbitmq.topics.sender.domain.DomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Rest Controller to provide Receiver applications an API to fetch all {@link Domain}s and update their cache.
 */
@RestController
public class DomainApi {
	
	private final DomainService domainService;
	
	public DomainApi(DomainService domainService) {
		this.domainService = domainService;
	}
	
	@GetMapping(URL.API_DOMAIN)
	public ResponseEntity<Collection<Domain>> getAllDomains() {
		final Collection<Domain> domains = this.domainService.getAllDomains();
		return ResponseEntity.ok(domains);
	}

}