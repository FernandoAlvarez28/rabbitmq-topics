package alvarez.fernando.rabbitmq.topics.sender.web;

import alvarez.fernando.rabbitmq.topics.sender.domain.Domain;
import alvarez.fernando.rabbitmq.topics.sender.domain.DomainNotFoundException;
import alvarez.fernando.rabbitmq.topics.sender.domain.DomainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

/**
 * Basic Controller to easily manage the {@link Domain}.
 */
@Controller
public class DomainController {
	
	private final DomainService domainService;
	
	public DomainController(DomainService domainService) {
		this.domainService = domainService;
	}
	
	@GetMapping({URL.INDEX, URL.APP, URL.APP_DOMAIN})
	public String home(Model model) {
		model.addAttribute("domains", this.domainService.getAllDomains());
		model.addAttribute("postUrl", URL.APP_DOMAIN);
		
		return "domains";
	}
	
	@PostMapping(URL.APP_DOMAIN)
	public String createDomain(@RequestParam String name) {
		this.domainService.createDomain(name);
		return URL.redirect(URL.APP_DOMAIN);
	}
	
	@PostMapping(URL.APP_DOMAIN_UUID_DELETE)
	public String deleteDomain(@PathVariable UUID domainUuid, RedirectAttributes redirectAttributes) {
		try {
			this.domainService.deleteDomain(domainUuid);
		} catch (DomainNotFoundException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		
		return URL.redirect(URL.APP_DOMAIN);
	}

}