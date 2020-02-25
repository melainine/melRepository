package microservice.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import microservice.model.AppUser;
import microservice.service.SessionService;
import microservice.service.SocialService;

@Controller

public class FacebookController {

	@Autowired
	private SocialService facebookService;

	@Autowired
	private AppUser appUser;

	@Autowired
	SessionService sessionService;


	@GetMapping(value = "/oauth2/facebook")
	public RedirectView facebooklogin() {
		RedirectView redirectView = new RedirectView();
		String url = facebookService.facebooklogin();
		redirectView.setUrl(url);
		return redirectView;

	}

	@GetMapping(value = "/facebook")
	public String facebook(@RequestParam("code") String code) {
		String accessToken = facebookService.getFacebookAccessToken(code);
		return "redirect:/facebookprofiledata/" + accessToken;

	}

	@GetMapping(value = "/facebookprofiledata/{accessToken:.+}")
	public ModelAndView facebookprofiledata(@PathVariable String accessToken, ModelAndView mv,
			HttpServletRequest request) {
		mv.setViewName("home");

		User user = facebookService.getFacebookUserProfile(accessToken);
		appUser.setFirstName(user.getFirstName());
		appUser.setLastName(user.getLastName());
		System.out.println("the status is: " + sessionService.loggedIn(request));
		ArrayList<String> list = new ArrayList<String>();
		list.add(user.getFirstName());
		list.add(user.getLastName());
		mv.addObject("loggedin", true);
		mv.addObject("name", list.get(0) + " " + list.get(1));

		return mv;
	}

}
