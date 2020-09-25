package alvarez.fernando.rabbitmq.topics.sender.web;

public class URL {
	
	public static final String API_DOMAIN = "/api/domains";
	
	public static final String INDEX = "/";
	public static final String APP = "/app";
	public static final String APP_DOMAIN = "/app/domains";
	public static final String APP_DOMAIN_UUID_DELETE = "/app/domains/{domainUuid}/delete";
	
	private static final String REDIRECT = "redirect:";
	
	private URL() {}
	
	public static String redirect(String destinationUrl) {
		return REDIRECT + destinationUrl;
	}
	
}