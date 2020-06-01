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

import java.util.List;
import java.util.Optional;

import microservice.model.AppUser;
import microservice.model.Device;
import microservice.repository.DeviceRepository;
import microservice.service.SessionService;
import microservice.service.SocialService;
import microservice.service.UserService;

@Controller
public class FacebookController {

	@Autowired
	private SocialService facebookService;

	@Autowired
	SessionService sessionService;

	@Autowired
	private UserService userService;

	@Autowired
	private DeviceRepository deviceRepository;

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
		AppUser dbUser = userService.findByEmail(user.getEmail());
		if (dbUser != null) {

			// System.out.println("Devices: "
			// +deviceRepository.findAllById(dbUser.getUserId()));
			// System.out.println("Services: "
			// +userService.findAllById(dbUser.getUserId()));

			mv.addObject("loggedin", true);
			mv.addObject("cover", user.getCover());
			mv.addObject("name", user.getFirstName() + " " + user.getLastName());

			System.out.println("___________Devices:____________ " + deviceRepository.findAllByEmail(dbUser.getEmail()));

			mv.addObject("counts", deviceRepository.findAllByEmail(dbUser.getEmail()));

			return mv;
		} else {
			String role = "FACEBOOK_USER";
			AppUser appUser = new AppUser(user.getFirstName(), user.getLastName(), user.getEmail());
			appUser.setEmail(user.getEmail());
			// appUser.setEnabled(true);
			appUser.setRole(role);

			userService.save(appUser);

			System.out.println("AppUser: " + appUser);
			System.out.println("FacebookUser: " + user);

			mv.addObject("loggedin", true);
			mv.addObject("cover", user.getCover());
			mv.addObject("name", user.getFirstName() + " " + user.getLastName());
			mv.addObject("counts", deviceRepository.findAllByEmail(user.getEmail()));


			return mv;
		}
	}

}
