package kg.alatoo.midterm_project.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import kg.alatoo.midterm_project.entity.RefreshToken;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.repository.RefreshTokenRepository;
import kg.alatoo.midterm_project.repository.UserRepository;
import kg.alatoo.midterm_project.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenImpl implements RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }
  public RefreshToken createRefreshToken(String username) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setExpiryDate(Instant.now().plusMillis(7 * 24 * 60 * 60 * 1000));

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException("User not found"));

    refreshToken.setUser(user);
    return refreshTokenRepository.save(refreshToken);
  }
  public void saveToken(String token, User user) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(token);
    refreshToken.setUser(user);
    refreshToken.setExpiryDate(Instant.now().plusMillis(7 * 24 * 60 * 60 * 1000));
    refreshTokenRepository.save(refreshToken);
  }

  public void deleteTokenByUserId(Long userId) {
    refreshTokenRepository.deleteByUserId(userId);
  }

  public void deleteToken(String token) {
    findByToken(token).ifPresent(refreshTokenRepository::delete);
  }
  public void verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().isBefore(Instant.now())) {
      refreshTokenRepository.delete(token);
      throw new RuntimeException("Refresh token was expired. Please make a new login request");
    }
  }

}
