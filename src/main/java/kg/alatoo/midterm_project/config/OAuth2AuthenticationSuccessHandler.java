package kg.alatoo.midterm_project.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kg.alatoo.midterm_project.entity.RefreshToken;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.entity.oauth2.UserPrincipal;
import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.repository.UserRepository;
import kg.alatoo.midterm_project.service.RefreshTokenService;
import kg.alatoo.midterm_project.service.impl.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  private final JwtService jwtService;
  private final RefreshTokenService refreshTokenService;
  private final UserRepository userRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException {

    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    String email = oAuth2User.getAttribute("email");

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("User not found"));

    String accessToken = jwtService.generateAccessToken(user);
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

    String redirectUrl = String.format(
        "http://localhost:8080/api/auth/oauth2/success?token=%s&refreshToken=%s",
        accessToken,
        refreshToken.getToken()
    );

    response.sendRedirect(redirectUrl);
  }
}