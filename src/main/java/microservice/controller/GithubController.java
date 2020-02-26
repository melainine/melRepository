package microservice.controller;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.github.api.GitHubUserProfile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import microservice.service.SocialService;

@Controller
public class GithubController {
	@Autowired
	private SocialService githubService;
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GithubController.class);

	@GetMapping(value = "/oauth2/github")
	public RedirectView githublogin() {
		RedirectView redirectView = new RedirectView();
		String url = githubService.githublogin();
		redirectView.setUrl(url);
		return redirectView;

	}

	@GetMapping(value = "/user/oauth2/github/callback")
	public String github(@RequestParam("code") String code) {
		String accessToken = githubService.getGithubAccessToken(code);
		return "redirect:/githubprofiledata/" + accessToken;

	}

	@GetMapping(value = "/githubprofiledata/{accessToken:.+}")
	public ModelAndView githubprofiledata(@PathVariable String accessToken, ModelAndView mv) {
//		mv.setViewName("home");
//		mv.addObject("loggedin", true);
//		return mv ;

		try {
			GitHubUserProfile user = githubService.getGithubUserProfile(accessToken);

			logger.info(user+" is logging");
			mv.setViewName("home");
			mv.addObject("loggedin", true);
			System.out.println("the user firstname is: " +user.getEmail());
			mv.addObject("name", user.getUsername());
			return mv;
			
		} catch (Exception e) {
			mv.addObject("loggedinerror", true);
			mv.addObject("message", e.getMessage());
			return mv ;
		}
		
	}
}
