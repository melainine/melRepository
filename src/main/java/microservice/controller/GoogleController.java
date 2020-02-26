package microservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
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
		return "redirect:/googleprofiledata/" + accessToken;

	}

	@GetMapping(value = "/googleprofiledata/{accessToken:.+}")
	public ModelAndView googleprofiledata(@PathVariable String accessToken, ModelAndView mv) {
		mv.setViewName("home");
		mv.addObject("loggedin", true);
		return mv ;

//		try {
//			Person user = googleService.getGoogleUserProfile(accessToken);
//
//			mv.setViewName("home");
//			mv.addObject("loggedin", true);
//			System.out.println("the user firstname is: " +user.getGivenName());
//			mv.addObject("cover", user.getImageUrl());
//			mv.addObject("name", user.getGivenName()+ " " + user.getFamilyName());
//			return mv;
//			
//		} catch (Exception e) {
//			mv.addObject("loggedinerror", true);
//			mv.addObject("message", e.getMessage());
//			return mv ;
//		}
		
	}


}
