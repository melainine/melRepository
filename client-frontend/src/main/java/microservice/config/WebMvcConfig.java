package microservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/bootstrap/**").addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/4.2.1/");
		registry.addResourceHandler("/jquery/**").addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.3.1-1/");
		registry.addResourceHandler("/popper/**").addResourceLocations("classpath:/META-INF/resources/webjars/popper.js/1.14.6/umd/");
	}
}
