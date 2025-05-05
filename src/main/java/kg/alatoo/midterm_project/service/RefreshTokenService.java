package kg.alatoo.midterm_project.service;

import java.util.Optional;
import kg.alatoo.midterm_project.entity.RefreshToken;
import kg.alatoo.midterm_project.entity.User;

public interface RefreshTokenService {
  Optional<RefreshToken> findByToken(String token);
  void saveToken(String token, User user);
  void deleteTokenByUserId(Long userId);
  void deleteToken(String token);
  void verifyExpiration(RefreshToken token);
  RefreshToken createRefreshToken(String username);

}
