package kg.alatoo.midterm_project.controller.auth;

import static org.mockito.Mockito.mock;

import kg.alatoo.midterm_project.config.OAuth2AuthenticationSuccessHandler;
import kg.alatoo.midterm_project.repository.UserRepository;
import kg.alatoo.midterm_project.service.RefreshTokenService;
import kg.alatoo.midterm_project.service.impl.JwtService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestSecurityConfig {

  @Bean
  @Primary
  public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(request -> request.disable())
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()
        )
        .build();
  }

  @Bean
  @Primary
  public OAuth2AuthenticationSuccessHandler testOAuth2SuccessHandler() {
    return mock(OAuth2AuthenticationSuccessHandler.class);
  }

  @Bean
  @Primary
  public JwtService testJwtService() {
    return mock(JwtService.class);
  }
}