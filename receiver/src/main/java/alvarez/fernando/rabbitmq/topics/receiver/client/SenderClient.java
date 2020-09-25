package alvarez.fernando.rabbitmq.topics.receiver.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class SenderClient extends AbstractRestClient {
	
	private final URI domainApi;
	
	public SenderClient(RestTemplateBuilder restTemplateBuilder, @Value("${sender.host}") String senderHost) throws URISyntaxException {
		super(restTemplateBuilder);
		this.domainApi = new URI(senderHost + "/api/domains");
	}
	
	@SuppressWarnings("unchecked")
	public List<DomainTO> fetchAllDomains() throws ClientException {
		return super.get(this.domainApi, new HttpEntity<>(new HttpHeaders()), List.class).getBody();
	}
	
}