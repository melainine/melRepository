package microservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import microservice.service.SessionService;

@Controller
public class HomeController {
	
	@Autowired
	SessionService sessionService;

	@RequestMapping(value = {"","/"})
	public ModelAndView home(ModelAndView mv, HttpServletRequest httpRequest) {
		mv.setViewName("home");
		mv.addObject("loggedin", sessionService.loggedIn(httpRequest));
		return mv;
	}
}
