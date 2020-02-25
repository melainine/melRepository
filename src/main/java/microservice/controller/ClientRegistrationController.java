package microservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import microservice.model.ClientDTO;
import microservice.service.SessionService;

@Controller
@RequestMapping("client")
public class ClientRegistrationController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientRegistrationController.class);
	
	@Autowired
	SessionService sessionService;
	
	@Value("${base-uri}")
	private String baseUri;
	
	@Value("${client-registration-api-service-name}")
	private String clientRegistrationServiceName;
	
	@RequestMapping(value = {"","/"})
	public ModelAndView home(ModelAndView mv, HttpServletRequest httpRequest) {
		try {
			mv.setViewName("home");
			mv.addObject("registration", true);
			mv.addObject("loggedin", sessionService.loggedIn(httpRequest));
			mv.addObject("clientDTO", new ClientDTO());
			
		} catch(Exception e) {
			logger.error("", e);
			mv.addObject("error", e.getMessage());
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewClient(@ModelAttribute("clientDTO") @Valid ClientDTO clientDTO, BindingResult result, ModelAndView modelAndView, HttpServletRequest httpRequest) {
		try {
			logger.info("Request to create Client: " + clientDTO);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("clientId", clientDTO.getClientId());
			map.add("redirectUri", clientDTO.getUri());
			map.add("info", clientDTO.getAdditionalInformation());
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			
			ResponseEntity<BaseClientDetails> response = new RestTemplate().postForEntity(baseUri + clientRegistrationServiceName + "/client/create", request, BaseClientDetails.class);
			
			BaseClientDetails createdClient = response.getBody();
			logger.info("Created new Client: " + createdClient);
			
			modelAndView.addObject("client", createdClient);
		} catch(Exception e) {
			logger.error("", e);
			modelAndView.addObject("error", e.getMessage());
		}
		return home(modelAndView, httpRequest);
	}
}
