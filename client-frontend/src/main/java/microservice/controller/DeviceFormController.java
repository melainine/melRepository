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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("deviceform")
public class DeviceFormController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceFormController.class);
	
	@Autowired
	DeviceController deviceController;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ModelAndView getDevices(ModelAndView mv,
			@RequestParam(value = "mycheckbox", required = false, defaultValue = "false") boolean mycheckbox, HttpServletRequest request) {
		logger.info("getDevices (Detail=" + mycheckbox + ")...");
		mv.setViewName("home");
		mv.addObject("devices", true);		
		try {
			ResponseEntity<String> response = deviceController.getAllDevices();
			mv.addObject("result", response.getBody());
				
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return deviceController.home(mv, request);
	}
	
	@RequestMapping(value = "/getDeviceById", method = RequestMethod.GET)
	public ModelAndView getDeviceById(@RequestParam String id, ModelAndView mv, HttpServletRequest request) {
		logger.info("getDeviceById (Id=" + id + ")...");
		mv.setViewName("home");
		mv.addObject("devices", true);
		try {
			ResponseEntity<String> response = deviceController.getDeviceById(id);
			mv.addObject("result", response.getBody());
			
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return deviceController.home(mv, request);
	}
	
	@RequestMapping("/getChannelOfDeviceById")
	public ModelAndView getChannelOfDeviceById(@RequestParam String deviceid, @RequestParam String channelid,
			ModelAndView mv, HttpServletRequest request) {
		logger.info("getChannelOfDeviceById (DeviceId=" + deviceid + " ChannelId=" + channelid + ")...");
		mv.setViewName("home");
		mv.addObject("devices", true);		
		try {
			ResponseEntity<String> response = deviceController.getChannelOfDeviceById(deviceid, channelid);
			mv.addObject("result", response.getBody());
			
		} catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return deviceController.home(mv, request);
	}

	@RequestMapping("/getHardwareOfDeviceById")
	public ModelAndView getHardwareOfDeviceById(@RequestParam String device, @RequestParam String hardware,
			ModelAndView mv, HttpServletRequest request) {
		logger.info("getChannelOfDeviceById (DeviceId=" + device + " HardwareId=" + hardware + ")...");
		mv.setViewName("home");
		mv.addObject("devices", true);
		try {
			ResponseEntity<String> response = deviceController.getHardwareOfDeviceById(device, hardware);
			mv.addObject("result", response.getBody());
		
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return deviceController.home(mv, request);
	}
	
	@RequestMapping("/updateDevice")
	public ModelAndView updateDevice(@RequestParam String device, 
										@RequestParam String description, 
										@RequestParam String label,
										ModelAndView mv, 
										HttpServletRequest request) {
		logger.info("updateDevice (DeviceId=" + device + " description=" + description + " label=" + label +")...");
		mv.setViewName("home");
		mv.addObject("devices", true);
		try {
			ResponseEntity<Void> response = deviceController.updateDevice(device, description, label);
			mv.addObject("result", response.getBody());
		
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return deviceController.home(mv, request);
	}
	
}
