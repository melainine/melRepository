package microservice.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import microservice.service.SessionService;
import microservice.service.SocialService;

@Controller
public class FacebookController {

	@Autowired
	private SocialService facebookService;

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
		mv.addObject("loggedin", true);
		mv.addObject("cover", user.getCover());
		mv.addObject("name", user.getFirstName() + " " + user.getLastName());
		//System.out.println(user.getCover());

		return mv;
	}

}
