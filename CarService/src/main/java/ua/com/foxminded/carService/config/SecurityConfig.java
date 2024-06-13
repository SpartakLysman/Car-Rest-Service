package ua.com.foxminded.carService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${auth0.audience}")
	private String audience;

	@Value("${auth0.issuer}")
	private String issuer;

	protected void configure(HttpSecurity http) throws Exception {
		JwtWebSecurityConfigurer.forRS256(audience, issuer).configure(http).authorizeRequests()
				.requestMatchers(HttpMethod.GET, "/api/v1/cars/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/manufacturers/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
				.requestMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated().anyRequest().authenticated();
	}
}

// http.authorizeRequests().requestMatchers(HttpMethod.GET,
// "/api/menu/items/**").permitAll() // GET requests don't																						
//				.anyRequest().authenticated().and().oauth2ResourceServer().jwt();
//	}
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		JwtWebSecurityConfigurer.forRS256(audience, issuer).configure(http).authorizeRequests()
//				.antMatchers(HttpMethod.GET, "/api/**").permitAll().antMatchers(HttpMethod.POST, "/api/**")
//				.authenticated().anyRequest().authenticated();
//	}
