package microservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import microservice.service.SessionService;

@Controller
@RequestMapping("parameters")
public class ParameterController {
	
	@Autowired
	SessionService sessionService;

	private static final Logger logger = LoggerFactory.getLogger(ParameterController.class);

	@Autowired
	private OAuth2RestTemplate restTemplate;
	
	@Autowired
	private HttpHeaders headers;
	
	@Value("${base-uri}")
	private String baseUri;
	
	@RequestMapping(value = {"","/"})
	public ModelAndView home(ModelAndView mv, HttpServletRequest httpRequest) {
		mv.setViewName("home");
		mv.addObject("parameters", true);
		mv.addObject("loggedin", sessionService.loggedIn(httpRequest));
		return mv;
	}

	@RequestMapping(value = "/{parameterId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getParameterById(@PathVariable("parameterId") String parameterId) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate
				.exchange(baseUri + "ict-gw/v1/parameters/" + parameterId, HttpMethod.GET, entity, String.class);
		logger.info(response.getBody());
		return response;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getParameters() {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(baseUri + "ict-gw/v1/parameters/", HttpMethod.GET, entity, String.class);
		logger.info(response.getBody());
		return response;
	}

	@RequestMapping(value = "/{parameterId}/info", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getParameterInfoById(@PathVariable("parameterId") String parameterId) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate
				.exchange(baseUri + "ict-gw/v1/parameters/" + parameterId + "/info", HttpMethod.GET, entity, String.class);
		logger.info(response.getBody());
		return response;
	}

	@RequestMapping(value = "/{parameterId}/samples/{storageId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Void> getParameterSamplesById(@PathVariable("parameterId") String parameterId,
			@PathVariable("storageId") String storageId,
			@RequestParam(value = "start", required = true) BigDecimal start,
			@RequestParam(value = "end", required = true) BigDecimal end) {
		HashMap<String, BigDecimal> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		HttpEntity<HashMap<String, BigDecimal>> entity2 = new HttpEntity<>(map, headers);
		ResponseEntity<Void> response = restTemplate.exchange(baseUri + "ict-gw/v1/parameters/" + parameterId + "/samples/" + storageId, HttpMethod.GET, entity2, Void.class);
		return response;
	}

	@RequestMapping(value = "/{parameterId}/value", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getParameterValueById(@PathVariable("parameterId") String parameterId){
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate
				.exchange(baseUri + "ict-gw/v1/parameters/" + parameterId + "/value", HttpMethod.GET, entity, String.class);
		logger.info(response.getBody());
		return response;
	}
	
	 @RequestMapping(value = "/{parameterId}/storages/{storageId}", method = RequestMethod.GET)
	 @ResponseBody
	 public ResponseEntity<String> getStorageById(@PathVariable("parameterId") String parameterId, @PathVariable("storageId") String storageId){
		 HttpEntity<String> entity = new HttpEntity<>(headers);
		 ResponseEntity<String> response = restTemplate
					.exchange(baseUri + "ict-gw/v1/parameters/" + parameterId + "/storages/" + storageId, HttpMethod.GET, entity, String.class);
			logger.info(response.getBody());
			return response;
	 }
	 
	 @RequestMapping(value = "/{parameterId}/info", method = RequestMethod.PUT)
	 @ResponseBody
	 public ResponseEntity<Void> updateParameterInfo(@PathVariable("parameterId") String parameterId,
			 											@RequestParam(value="description", required=false) String description, 
			 											@RequestParam(value="label", required=false)  String label){
		 HashMap<String, String> map = new HashMap<>();
		 map.put("description", description);
		 map.put("label", label);
		 HttpEntity<HashMap<String, String>> entity2 = new HttpEntity<>(map, headers);
		 ResponseEntity<Void> response = restTemplate.exchange(baseUri + "ict-gw/v1/parameters/" + parameterId + "/info", HttpMethod.PUT, entity2, Void.class);
		 return response;
	 }
	 
	 @RequestMapping(value = "/{parameterId}/value", method = RequestMethod.PUT)
	 @ResponseBody
	 public ResponseEntity<Void> updateParameterValue(@PathVariable("parameterId") String parameterId, @RequestParam(value="value", required=false) String value){
		 HashMap<String, String> map = new HashMap<>();
		 map.put("value", value);
		 HttpEntity<HashMap<String, String>> entity2 = new HttpEntity<>(map, headers);
		 ResponseEntity<Void> response = restTemplate.exchange(baseUri + "ict-gw/v1/parameters/" + parameterId + "/value", HttpMethod.PUT, entity2, Void.class);
		 return response;
	 }
}
