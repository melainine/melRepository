package microservice.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import microservice.service.SessionService;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private OAuth2RestTemplate restTemplate;
	
	@Autowired
	@Qualifier("authCodeRestTemplate")
	private OAuth2RestTemplate authCodeRestTemplate;
	
	@Autowired
	SessionService sessionService;
	
	//auth code token request
	@RequestMapping("/user")
	public String userLogin() {
		logger.info("Logging in...");

		String token = authCodeRestTemplate.getAccessToken().getValue();
		logger.info(token);
		return token;
	}


	@RequestMapping(value = "/loginpage", method = RequestMethod.GET)
	public ModelAndView loginpage(ModelAndView mv) {
		mv.setViewName("loginpage");
		return mv;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
		try {
			logger.info("Login: " + username + ":" + password);
			restTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("username", username);
			restTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("password", password);
			restTemplate.getOAuth2ClientContext().getAccessTokenRequest().add("X-Host-Override", "authorization-server");
									
			String token = restTemplate.getAccessToken().getValue();
			logger.info(token);
			
			return new ResponseEntity<String>(token, HttpStatus.OK);
		} catch(Exception e) {
			logger.error("",e);
			logger.info("Sending Error Response...");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/loginfunction", method = RequestMethod.POST)
	public ModelAndView loginfunction(@RequestParam String username, @RequestParam String password, ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("home");
		//ResponseEntity<String> login = login(username, password);
		ResponseEntity<String> login = sessionService.login(username, password, request);
		logger.info(login.getBody());
		if(login.getStatusCode() == HttpStatus.OK) {
			mv.addObject("login", login.getBody());
			mv.addObject("loggedin", sessionService.loggedIn(request));
		} else {
			mv.addObject("loginerror", login.getBody());
		}
		return mv;
	}
	
	@RequestMapping(value = "/logoutfunction", method = RequestMethod.POST)
	public ModelAndView logout(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("home");
		sessionService.logout(request);
		mv.addObject("loggedin", sessionService.loggedIn(request));
		return mv;
	}
}
