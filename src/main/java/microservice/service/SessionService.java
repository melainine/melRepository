package microservice.service;

import java.util.Base64;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;


@Service
public class SessionService {
	
	@Autowired
	private OAuth2RestTemplate restTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(SessionService.class);
	
	private HashMap<String, HttpSession> sessions;
	
	public SessionService() {
		this.sessions = new HashMap<>();
	}
	
	public ResponseEntity<String> login(String username, String password, HttpServletRequest httpRequest){
		try {
			logger.info("Login: " + username + ":" + password);
			restTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("username", username);
			restTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("password", password);
			restTemplate.getOAuth2ClientContext().getAccessTokenRequest().add("X-Host-Override", "authorization-server");
									
			String token = restTemplate.getAccessToken().getValue();
			logger.info(token);
			
			HttpSession session = httpRequest.getSession(true);
			logger.info("Session: " + session.toString());
			session.setAttribute("token", token);
			sessions.put(session.getId(), session);
			
			return new ResponseEntity<String>(token, HttpStatus.OK);
		} catch(Exception e) {
			logger.error("",e);
			logger.info("Sending Error Response...");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public void logout(HttpServletRequest httpRequest) {
		try {
			logger.info("Logout...");
			
			//Logout Client App
			HttpSession session = httpRequest.getSession(false);
			sessions.remove(session.getId());
			restTemplate.getOAuth2ClientContext().getAccessTokenRequest().remove("username");
			restTemplate.getOAuth2ClientContext().getAccessTokenRequest().remove("password");
			
			//Logout Auth Server -> invalidate Token
			String uri = restTemplate.getResource().getAccessTokenUri();
			HttpHeaders headers = new HttpHeaders();
			String auth = restTemplate.getResource().getClientId() + ":" + restTemplate.getResource().getClientSecret();
			byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
			String authHeader = "Basic " + new String(encodedAuth);
			headers.add("Authorization", authHeader);
			headers.add("Accesstoken", restTemplate.getOAuth2ClientContext().getAccessToken().getValue());
			headers.add("X-Host-Override", "authorization-server");
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, Void.class);
			logger.info("Response Status: " + response.getStatusCodeValue());
			
			OAuth2ClientContext context = restTemplate.getOAuth2ClientContext();
			context.setAccessToken(null);
			
			logger.info("Logout success");
		} catch(Exception e) {
			logger.error("", e);
		}
	}
	
	public boolean loggedIn(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		try {
			return sessions.containsKey(session.getId());
		} catch(Exception e) {
			logger.error("", e);
			return false;
		}
	}
}
