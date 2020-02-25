package microservice.controller;

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
@RequestMapping("devices")
public class DeviceController {
	
	@Autowired
	SessionService sessionService;

	// @formatter:off
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	private OAuth2RestTemplate restTemplate;
	
	@Autowired
	private HttpHeaders headers;
		
	@Value("${base-uri}")
	private String baseUri;
		
	@RequestMapping(value = {"","/"})
	public ModelAndView home(ModelAndView mv, HttpServletRequest httpRequest) {
		mv.setViewName("home");
		mv.addObject("devices", true);
		mv.addObject("loggedin", sessionService.loggedIn(httpRequest));
		return mv;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getAllDevices() {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(baseUri + "ict-gw/v1/devices", HttpMethod.GET, entity, String.class);
		logger.info(response.getBody());
		return response;
	}

	@RequestMapping(value = "/{deviceId}/channels/{channelId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getChannelOfDeviceById(@PathVariable("deviceId") String deviceId, @PathVariable("channelId") String channelId) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(baseUri + "ict-gw/v1/devices/" + deviceId + "/channels/" + channelId, HttpMethod.GET, entity, String.class);
		logger.info(response.getBody());
		return response;
	}

	@RequestMapping(value = "/{deviceId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getDeviceById(@PathVariable("deviceId") String deviceId) {
		logger.info("Header: " + headers.toString());
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(baseUri + "ict-gw/v1/devices/" + deviceId, HttpMethod.GET, entity, String.class);
		logger.info(response.getBody());
		return response;
	}
	
	
	 @RequestMapping(value = "/{deviceId}/hardware/{hardwareId}", method = RequestMethod.GET)
	 @ResponseBody
	 public ResponseEntity<String> getHardwareOfDeviceById(@PathVariable("deviceId") String deviceId, @PathVariable("hardwareId") String hardwareId){
		 HttpEntity<String> entity = new HttpEntity<>(headers);
		 ResponseEntity<String> response = restTemplate.exchange(baseUri + "ict-gw/v1/devices/" + deviceId + "/hardware/" + hardwareId, HttpMethod.GET, entity, String.class);
			logger.info(response.getBody());
			return response;
	 }
	
	 @RequestMapping(value = "/{deviceId}", method = RequestMethod.PUT)
	 @ResponseBody
	 public ResponseEntity<Void> updateDevice(@PathVariable("deviceId") String deviceId, 
			 									@RequestParam(value="description", required=false)  String description, 
			 									@RequestParam(value="label", required=false) String label){
		 HashMap<String, String> map = new HashMap<>();
		 map.put("description", description);
		 map.put("label", label);
		 HttpEntity<HashMap<String, String>> entity2 = new HttpEntity<HashMap<String, String>>(map, headers);
		 ResponseEntity<Void> response = restTemplate.exchange(baseUri + "ict-gw/v1/devices/" + deviceId, HttpMethod.PUT, entity2, Void.class);
		 return response;
	 }
	 
	 // @formatter:on
}
