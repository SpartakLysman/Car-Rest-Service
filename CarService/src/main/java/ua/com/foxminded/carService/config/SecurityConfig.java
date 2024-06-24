package ua.com.foxminded.carService.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final String audience;
	private final String issuer;

	public SecurityConfig(@Value("${auth0.audience}") String audience,
			@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuer) {
		this.audience = audience;
		this.issuer = issuer;
	}

	protected void configure(HttpSecurity http) throws Exception {
		JwtWebSecurityConfigurer.forRS256(audience, issuer).configure(http).authorizeRequests()
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/cars/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/manufacturers/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
				.requestMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated().anyRequest().authenticated().and()
				.cors().configurationSource(corsConfigurationSource()).and().oauth2ResourceServer().jwt()
				.decoder(jwtDecoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.POST.name(),
				HttpMethod.DELETE.name()));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
		return source;
	}

	@Bean
	JwtDecoder jwtDecoder() {
		OAuth2TokenValidator<Jwt> withAudience = new OAuth2TokenValidator<Jwt>(audience);
		OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
		OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(withAudience, withIssuer);

		NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuer);
		jwtDecoder.setJwtValidator(validator);
		return jwtDecoder;
	}
}