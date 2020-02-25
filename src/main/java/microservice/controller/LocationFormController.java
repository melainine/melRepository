package microservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("locationform")
public class LocationFormController {
	
	private static final Logger logger = LoggerFactory.getLogger(LocationFormController.class);
	
	@Autowired
	LocationController locationController;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getLocations(ModelAndView mv,
			@RequestParam(value = "mycheckbox", required = false, defaultValue = "false") boolean mycheckbox, HttpServletRequest request) {
		logger.info("getLocations (Detail=" + mycheckbox + ")...");
		mv.setViewName("home");
		mv.addObject("locations", true);
		try {
			ResponseEntity<String> response = locationController.getLocations();
			mv.addObject("result", response.getBody());
			
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return locationController.home(mv, request);
	}
	
	@RequestMapping(value = "/getLocationById", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getLocationById(@RequestParam String id, ModelAndView mv, HttpServletRequest request) {
		logger.info("getLocationById (Id=" + id + ")...");
		mv.setViewName("home");
		mv.addObject("locations", true);
		try {
			ResponseEntity<String> response = locationController.getLocationById(id);
			mv.addObject("result", response.getBody());
			
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return locationController.home(mv, request);
	}
}
