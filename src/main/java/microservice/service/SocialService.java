package microservice.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.User;
import org.springframework.social.github.api.GitHubUser;
import org.springframework.social.github.api.GitHubUserProfile;
import org.springframework.social.google.api.plus.Person;

public interface SocialService {

	String facebooklogin();
	String getFacebookAccessToken(String code);
	User getFacebookUserProfile(String accessToken);
	
	String googlelogin();
	String getGoogleAccessToken(String code);
	Person getGoogleUserProfile(String accessToken);
	
	String githublogin();
	String getGithubAccessToken(String code);
	GitHubUserProfile getGithubUserProfile(String accessToken);

}