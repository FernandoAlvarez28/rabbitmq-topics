package alvarez.fernando.rabbitmq.topics.receiver.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public abstract class AbstractRestClient {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final Duration DEFAULT_TIMEOUT = Duration.of(50, ChronoUnit.SECONDS);
	
	private final RestTemplate restTemplate;
	
	public AbstractRestClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.setConnectTimeout(DEFAULT_TIMEOUT).setReadTimeout(DEFAULT_TIMEOUT).build();
	}
	
	protected final <T> ResponseEntity<T> get(URI uri, HttpEntity<String> httpEntity, Class<T> responseType) throws ClientException {
		try {
			logger.info("Requesting GET to {}", uri);
			return this.restTemplate.exchange(uri, HttpMethod.GET, httpEntity, responseType);
		} catch (RestClientResponseException e) {
			throw new ClientException("Error during request", e);
		}
	}
	
}