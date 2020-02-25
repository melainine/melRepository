package microservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("parameterform")
public class ParameterFormController {
	
	private static final Logger logger = LoggerFactory.getLogger(ParameterFormController.class);
	
	@Autowired
	ParameterController parameterController;
	
	@RequestMapping("/get")
	public ModelAndView getParameters(ModelAndView mv,
			@RequestParam(value = "mycheckbox", required = false, defaultValue = "false") boolean mycheckbox, HttpServletRequest request) {
		logger.info("getParameters (Detail=" + mycheckbox + ")...");
		mv.setViewName("home");
		mv.addObject("parameters", true);
		try {	
			ResponseEntity<String> response = parameterController.getParameters();
			mv.addObject("result", response.getBody());
				
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return parameterController.home(mv, request);
	}
	
	@RequestMapping("/getparameter")
	public ModelAndView getParameterById(@RequestParam(value = "id", required = true) String id, ModelAndView mv,
			@RequestParam(value = "mycheckbox", required = false, defaultValue = "false") boolean mycheckbox, HttpServletRequest request) {
		logger.info("getParameterById (ParameterId=" + id + " Detail=" + mycheckbox + ")...");
		mv.setViewName("home");
		mv.addObject("parameters", true);
		try {	
			ResponseEntity<String> response = parameterController.getParameterById(id);
			mv.addObject("result", response.getBody());
				
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return parameterController.home(mv, request);
	}
	
	@RequestMapping("/getparameterInfo")
	public ModelAndView getParameterInfoById(@RequestParam String info, ModelAndView mv, HttpServletRequest request) {
		logger.info("getParameterInfoById (ParameterId=" + info + ")...");
		mv.setViewName("home");
		mv.addObject("parameters", true);
		try {	
			ResponseEntity<String> response = parameterController.getParameterInfoById(info);
			mv.addObject("result", response.getBody());
			
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return parameterController.home(mv, request);
	}
	
	@RequestMapping("/getparameterValue")
	public ModelAndView getParameterValueById(@RequestParam String value, ModelAndView mv, HttpServletRequest request) {
		logger.info("getParameterValueById (ParameterId=" + value + ")...");
		mv.setViewName("home");
		mv.addObject("parameters", true);
		try {	
			ResponseEntity<String> response = parameterController.getParameterValueById(value);
			mv.addObject("result", response.getBody());
			
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return parameterController.home(mv, request);
	}
	
	@RequestMapping("/getStorage")
	public ModelAndView getStorageById(@RequestParam String parameterid, @RequestParam String storageid,
			ModelAndView mv, HttpServletRequest request) {
		logger.info("getStorageById (ParameterId=" + parameterid + " StorageId=" + storageid + ")...");
		mv.setViewName("home");
		mv.addObject("parameters", true);
		try {	
			ResponseEntity<String> response = parameterController.getStorageById(parameterid, storageid);
			mv.addObject("result", response.getBody());
			
		}catch(Exception e) {
			logger.error("",e);
			mv.addObject("result", e.getMessage());

		}
		return parameterController.home(mv, request);
	}
	
	@RequestMapping("/getparameterSamples")
	public ModelAndView getParameterSamplesById(@RequestParam String parameter, @RequestParam String storage,
			ModelAndView mv, HttpServletRequest request) {
		logger.info("getParameterSamplesById (ParameterId=" + parameter + " StorageId=" + storage + ")...");
		mv.setViewName("home");
		mv.addObject("parameters", true);
		mv.addObject("result", HttpStatus.NOT_IMPLEMENTED);
		return mv;
	}
}
