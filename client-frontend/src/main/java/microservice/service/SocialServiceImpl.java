package microservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class SocialServiceImpl implements SocialService {
	
	@Value("${spring.social.facebook.appId}")
	private String facebookId;
	
	@Value("${spring.social.facebook.appSecret}")
	private String facebookSecret;
	
	@Value("${spring.social.google.appId}")
	private String googleId;
	
	@Value("${spring.social.google.appSecret}")
	private String googleSecret;
	
	private FacebookConnectionFactory createFacebookConnection() {
		return new FacebookConnectionFactory(facebookId, facebookSecret);
	}
		
	private GoogleConnectionFactory createGoogleConnection() {
		return new GoogleConnectionFactory(googleId, googleSecret);
	}
	
	@Override
	public String facebooklogin() {
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri("http://localhost:3000/facebook");
		parameters.setScope("public_profile,email");
		
		return createFacebookConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
	}

	@Override
	public String getFacebookAccessToken(String code) {
		return createFacebookConnection(). getOAuthOperations().exchangeForAccess(code, "http://localhost:3000/facebook", null).getAccessToken();
	}

	@Override
	public User getFacebookUserProfile(String accessToken) {
		Facebook facebook = new FacebookTemplate(accessToken);
		String[] fields = {"first_name", "last_name", "email"};
		return facebook.fetchObject("me", User.class, fields);
	}

	@Override
	public String googlelogin() {
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri("http://localhost:3000/google");
		parameters.setScope("userinfo.email");
		
		return createGoogleConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
	}

	@Override
	public String getGoogleAccessToken(String code) {
		return createGoogleConnection(). getOAuthOperations().exchangeForAccess(code, "http://localhost:3000/google", null).getAccessToken();
	}

	@Override
	public Person getGoogleUserProfile(String accessToken) {
		Google google = new GoogleTemplate(accessToken);
		Person person = google.plusOperations().getGoogleProfile();
		return person;
	}

}
