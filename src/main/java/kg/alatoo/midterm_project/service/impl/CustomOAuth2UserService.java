package kg.alatoo.midterm_project.service.impl;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.entity.oauth2.UserPrincipal;
import kg.alatoo.midterm_project.enums.AuthProvider;
import kg.alatoo.midterm_project.factory.OAuth2UserInfoFactory;
import kg.alatoo.midterm_project.repository.RoleRepository;
import kg.alatoo.midterm_project.repository.UserRepository;
import kg.alatoo.midterm_project.service.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    Map<String, Object> attributes = oAuth2User.getAttributes();
    String provider = userRequest.getClientRegistration().getRegistrationId();

    OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, attributes);

    if (userInfo.getEmail() == null || userInfo.getEmail().isEmpty()) {
      throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
    }

    Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());
    User user;

    if (userOptional.isPresent()) {
      user = userOptional.get();
    } else {
      user = registerNewUser(userInfo, provider);
    }

    return UserPrincipal.create(user, attributes);
  }

  private User registerNewUser(OAuth2UserInfo userInfo, String provider) {
    User user = new User();
    user.setUsername(userInfo.getName());
    user.setEmail(userInfo.getEmail());
    user.setPassword(passwordEncoder.encode("OAUTH2_" + UUID.randomUUID()));
    user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
    user.setUserRole(roleRepository.findByName("USER")
        .orElseThrow(() -> new RuntimeException("Default role not found")));
    return userRepository.save(user);
  }
}
