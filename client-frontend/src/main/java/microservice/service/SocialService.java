package microservice.service;

import org.springframework.security.core.Authentication;
import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.plus.Person;
//import org.springframework.security.oauth2.oidc.user;



public interface SocialService {



	String facebooklogin();
	String getFacebookAccessToken(String code);
	User getFacebookUserProfile(String accessToken);
	
	String googlelogin();
	String getGoogleAccessToken(String code);
	Person getGoogleUserProfile(String accessToken);
//	Authentication getGoogleUserProfile(String accessToken);

}
