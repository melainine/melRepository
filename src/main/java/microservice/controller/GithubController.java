package microservice.controller;

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

	@GetMapping(value = "/oauth2/twitter")
	public RedirectView githublogin() {
		RedirectView redirectView = new RedirectView();
		String url = githubService.githublogin();
		redirectView.setUrl(url);
		return redirectView;

	}

	@GetMapping(value = "/github")
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

			mv.setViewName("home");
			mv.addObject("loggedin", true);
			System.out.println("the user firstname is: " +user.getEmail());
			mv.addObject("name", user.getName()+ " " + user.getUsername());
			return mv;
			
		} catch (Exception e) {
			mv.addObject("loggedinerror", true);
			mv.addObject("message", e.getMessage());
			return mv ;
		}
		
	}
}
