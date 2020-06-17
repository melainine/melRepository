package microservice.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

@EnableOAuth2Client
@Configuration
@PropertySource("classpath:social.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Value("${client-id}")
	private String clientID;

	@Value("${client-secret}")
	private String clientSecret;

	@Value("${access-token-uri}")
	private String accessTokenUri;
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class).authorizeRequests();
		http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
		http
        .formLogin()
        .loginPage("/loginpage")
      .and()
        .logout()
        .logoutSuccessUrl("/");
	}
	
	@Bean
	@Primary
	public OAuth2RestTemplate restTemplate() //OAuth2ClientContext oauth2ClientContext
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource(), clientContext());
		CustomResourceOwnerPasswordAccessTokenProvider provider = new CustomResourceOwnerPasswordAccessTokenProvider();
		
		//ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
	
		template.setAccessTokenProvider(provider);	

		SSLUtil.turnOffSslChecking();
		HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
				
		return template;
	}
		
	@Bean
	public OAuth2ProtectedResourceDetails resource() {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setClientId(clientID);
		resource.setClientSecret(clientSecret);
		resource.setAccessTokenUri(accessTokenUri);
		resource.setScope(Arrays.asList("read"));
		resource.setGrantType("password");
		resource.setClientAuthenticationScheme(AuthenticationScheme.form);
		
		return resource;
	}
	
	//--------------------------------------------------------------------------------------------------
	@Bean
	public AuthorizationCodeResourceDetails authCodeResource() {
		AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
		details.setClientId(clientID);
		details.setClientSecret(clientSecret);
		details.setAccessTokenUri(accessTokenUri);
		details.setScope(Arrays.asList("read","write"));
		details.setGrantType("authorization_code");
		details.setClientAuthenticationScheme(AuthenticationScheme.form);
		details.setPreEstablishedRedirectUri("http://localhost:8085/user");
		details.setUserAuthorizationUri("https://localhost:8443/authorization-server/auth/oauth/authorize");
		return details;
	}
	
	@Bean("authCodeRestTemplate")
	public OAuth2RestTemplate authCodeRestTemplate(OAuth2ClientContext oauth2ClientContext) throws KeyManagementException, NoSuchAlgorithmException {
		
		OAuth2RestTemplate template = new OAuth2RestTemplate(authCodeResource(), oauth2ClientContext);
			
		AuthorizationCodeAccessTokenProvider provider = new AuthorizationCodeAccessTokenProvider();

		template.setAccessTokenProvider(provider);
		
		SSLUtil.turnOffSslChecking();
		HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
				
		return template;		
	}

	//--------------------------------------------------------------------------------------------------
	@Bean
	public DefaultOAuth2ClientContext clientContext() {
		DefaultAccessTokenRequest request = new DefaultAccessTokenRequest();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Host-Override", "authorization-server");
		request.setHeaders(headers);

		DefaultOAuth2ClientContext context = new DefaultOAuth2ClientContext(request);
		
		return context;
	}
	
	@Bean
	public HttpHeaders headers() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Host-Override", "device-condition-api-oauth");
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//api key und jwt hinzufügen
		return headers;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
	private Filter ssoFilter() {

		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();


		OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/oauth2/google");
		OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
		googleFilter.setRestTemplate(googleTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
		tokenServices.setRestTemplate(googleTemplate);
		googleFilter.setTokenServices(tokenServices);

		filters.add(googleFilter);

		filter.setFilters(filters);

		return filter;
	}
	@Bean
	@ConfigurationProperties(prefix = "security.oauth2.client")
	public AuthorizationCodeResourceDetails google() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties(prefix = "security.oauth2.resource")
	public ResourceServerProperties googleResource() {
		return new ResourceServerProperties();
	}
}
