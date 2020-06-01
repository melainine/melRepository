package microservice.controller;


import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import microservice.model.AppUser;
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
	public ModelAndView googleprofiledata(@PathVariable String accessToken, ModelAndView mv,HttpServletRequest request, 
		      HttpServletResponse response, Authentication authentication, Principal principal) {
		HttpSession session = request.getSession(false);
		System.out.println("____________Session:_______"+session);
		System.out.println("____________principal:_______"+principal);

		System.out.println("____________Authentication:_______"+authentication);
		Person user = googleService.getGoogleUserProfile(accessToken);
		System.out.println("____________User:_______"+user);


		//AppUser user = new AppUser(authentication.getName(),authentication.getName(),authentication.getName());
		mv.setViewName("home");
		mv.addObject("loggedin", true);
//		System.out.println("_______User_______:"+user);
//		mv.addObject("name", user);

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
