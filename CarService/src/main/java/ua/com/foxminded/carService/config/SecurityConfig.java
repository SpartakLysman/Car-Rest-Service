package ua.com.foxminded.carService.config;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import ua.com.foxminded.carService.security.AudienceValidator;

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

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/cars/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/manufacturers/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated()
                                .anyRequest().authenticated())
                .oauth2ResourceServer(server -> server.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        OAuth2TokenValidator<Jwt> withAudience = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(withAudience, withIssuer);

        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);
        jwtDecoder.setJwtValidator(validator);
        return jwtDecoder;
    }
}