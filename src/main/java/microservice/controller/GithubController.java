package microservice.controller;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.social.github.api.GitHubUserProfile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import microservice.model.AppUser;
import microservice.repository.DeviceRepository;
import microservice.service.SocialService;
import microservice.service.UserService;

@Controller
public class GithubController {
	@Autowired
	private SocialService githubService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private DeviceRepository deviceRepository;
	
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
			GitHubUserProfile gtUser = githubService.getGithubUserProfile(accessToken);
			System.out.println("___________User_________"+gtUser.getEmail());
			AppUser dbUser = userService.findByEmail(gtUser.getEmail());
			if (dbUser != null) {

				// System.out.println("Devices: "
				// +deviceRepository.findAllById(dbUser.getUserId()));
				// System.out.println("Services: "
				// +userService.findAllById(dbUser.getUserId()));

				mv.addObject("loggedin", true);
				//mv.addObject("cover", gtUser.getCover());
				mv.addObject("name", gtUser.getName() + " " + gtUser.getUsername());

				System.out.println("___________Devices:____________ " + deviceRepository.findAllByEmail(dbUser.getEmail()));

				mv.addObject("counts", deviceRepository.findAllByEmail(dbUser.getEmail()));

				return mv;
			} else {
				String role = "GitHub_USER";
				AppUser appUser = new AppUser(gtUser.getName(), gtUser.getUsername(), gtUser.getEmail());
				appUser.setEmail(gtUser.getEmail());
				// appUser.setEnabled(true);
				appUser.setRole(role);

				userService.save(appUser);

				System.out.println("AppUser: " + appUser);
				System.out.println("GithubUser: " + gtUser.getEmail());

				mv.addObject("loggedin", true);
				//mv.addObject("cover", gtUser.getCover());
				
				mv.addObject("name", gtUser.getName() + " " + gtUser.getUsername());

				return mv;
			}
			
		} catch (Exception e) {
			mv.addObject("loggedinerror", true);
			mv.addObject("message", e.getMessage());
			return mv ;
		}
		
	}
}
