package microservice.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.servlet.ModelAndView;

import microservice.model.AppUser;
import microservice.model.Device;
import microservice.repository.DeviceRepository;
import microservice.service.UserService;

@RestController
public class GoogleController {

	@Autowired
	private UserService userService;

	@Autowired
	private DeviceRepository deviceRepository;

//	@RequestMapping("/googleuser")
//	public Principal user(Principal principal, ModelAndView mv) {
//		mv.setViewName("home");
//		mv.addObject("loggedin", true);
//
//		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
//		Authentication authentication = oAuth2Authentication.getUserAuthentication();
//		Map<String, String> details = new LinkedHashMap<>();
//		details = (Map<String, String>) authentication.getDetails();
//
//		System.out.println("_________type__________"+principal.getClass()+" ,");
//		AppUser dbUser = userService.findByEmail(details.get("email"));
//		if (dbUser != null) {
//
//			mv.addObject("hasDevices", true);
//			mv.addObject("devices", deviceRepository.findAllByEmail(dbUser.getEmail()));
//
//			return principal;
//
//		} else {
//			String role = "GOOGLE_USER";
//			AppUser appUser = new AppUser(details.get("given_name"), details.get("family_name"), details.get("email"));
//			appUser.setRole(role);
//
//			userService.save(appUser);
//
//			List <Device >devices = deviceRepository.findAllByEmail(appUser.getEmail());
//			mv.addObject("hasDevices", true);
//			mv.addObject("devices", deviceRepository.findAllByEmail(appUser.getEmail()));
//
//			return principal;
//		}
//
//	}
	@RequestMapping("/googleuser")

	public Map<Object, Object> googleUser(Principal principal, ModelAndView mv) {
		mv.setViewName("home");
		//mv.addObject("loggedin", true);

		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
		Authentication authentication = oAuth2Authentication.getUserAuthentication();
		Map<Object, Object> details = new LinkedHashMap<>();
		details = (Map<Object, Object>) authentication.getDetails();
		System.out.println("_____________Details___________" + details.get("email"));


		AppUser dbUser = userService.findByEmail((String) details.get("email"));

		if (dbUser != null) {

			mv.addObject("hasDevices", true);
			mv.addObject("devices", deviceRepository.findAllByEmail(dbUser.getEmail()));
			details.put("devices", deviceRepository.findAllByEmail(dbUser.getEmail()));


			return details;

		}
		String role = "GOOGLE_USER";
		AppUser appUser = new AppUser((String) details.get("given_name"), (String) details.get("family_name"), (String) details.get("email"));
		appUser.setRole(role);

		userService.save(appUser);

		List<Device> devices = deviceRepository.findAllByEmail(appUser.getEmail());
		mv.addObject("hasDevices", true);
		mv.addObject("devices", deviceRepository.findAllByEmail(appUser.getEmail()));

		return details;

	}

}
