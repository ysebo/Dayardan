package kg.alatoo.midterm_project.security;

import kg.alatoo.midterm_project.config.OAuth2AuthenticationSuccessHandler;
import kg.alatoo.midterm_project.filter.IpBlacklistFilter;
import kg.alatoo.midterm_project.filter.JwtAuthenticationFilter;
import kg.alatoo.midterm_project.filter.RateLimitingFilter;
import kg.alatoo.midterm_project.service.impl.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final OAuth2AuthenticationSuccessHandler oAuth2SuccessHandler;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final RateLimitingFilter rateLimitingFilter;
  private final IpBlacklistFilter ipBlacklistFilter;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/api/auth/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
            ).permitAll()
            .requestMatchers("/api/roles/**","/api/categories/**", "/api/questions/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .oauth2Login(oauth2 -> oauth2
            .successHandler(oAuth2SuccessHandler)
            .userInfoEndpoint(userInfo -> userInfo
                .userService(customOAuth2UserService)
            )
        )
        .addFilterBefore(rateLimitingFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .addFilterBefore(ipBlacklistFilter, RateLimitingFilter.class);
    return http.build();
  }

}
