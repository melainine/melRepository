package microservice.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import microservice.service.SocialService;

@Controller
public class GoogleController {

	@Autowired
	private SocialService googleService;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


	@GetMapping(value = "/oauth2/google")
	public RedirectView googlelogin() {
		RedirectView redirectView = new RedirectView();
		String url = googleService.googlelogin();
		redirectView.setUrl(url);
		return redirectView;

	}

	@GetMapping(value = "/google")
	public String google(@RequestParam("code") String code) {
		String accessToken = googleService.getGoogleAccessToken(code);
		logger.info("this is the AccessToken: "+ accessToken);

		return "redirect:/googleprofiledata/" + accessToken;

	}

	@GetMapping(value = "/googleprofiledata/{accessToken:.+}")
	public ModelAndView googleprofiledata(@PathVariable String accessToken, ModelAndView mv) {

		try {
			Person user = googleService.getGoogleUserProfile(accessToken);
			logger.info(user+ " is logging");
			mv.addObject("loggedin", true);
			mv.addObject("name", user.getGivenName() + " " + user.getFamilyName());

			return mv;
		} catch (Exception e) {
			logger.error("Error: "+e);;
			mv.addObject("loggedinerror", true);
			mv.addObject("error", e);
			return mv;

		}

	}

}
