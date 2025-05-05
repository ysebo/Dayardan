package kg.alatoo.midterm_project.service.impl;

import kg.alatoo.midterm_project.entity.RefreshToken;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.exceptions.AlreadyExistsException;
import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.mapper.UserMapper;
import kg.alatoo.midterm_project.payload.request.LoginRequest;
import kg.alatoo.midterm_project.payload.request.RefreshTokenRequest;
import kg.alatoo.midterm_project.payload.request.UserRequest;
import kg.alatoo.midterm_project.payload.response.JwtResponse;
import kg.alatoo.midterm_project.payload.response.UserResponse;
import kg.alatoo.midterm_project.repository.UserRepository;
import kg.alatoo.midterm_project.service.AuthService;
import kg.alatoo.midterm_project.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final RefreshTokenService refreshTokenService;
  private final AuthenticationManager authenticationManager;


  public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper,
      PasswordEncoder passwordEncoder, JwtService jwtService,
      RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager
  ) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.refreshTokenService = refreshTokenService;
    this.authenticationManager = authenticationManager;
  }

  public UserResponse register(UserRequest userRequest) {
    if (userRepository.findByUsername(userRequest.username()).isPresent()) {
      throw new AlreadyExistsException("Username is already taken!");
    }
    if (userRepository.findByEmail(userRequest.email()).isPresent()) {
      throw new AlreadyExistsException("Email is already in use!");
    }
    User user = userMapper.toEntity(userRequest);
    user.setPassword(passwordEncoder.encode(userRequest.password()));
    return userMapper.toModel(userRepository.save(user));
  }

  public JwtResponse login(LoginRequest request) {
    User userEntity = userRepository.findByUsername(request.username())
        .orElseThrow(() -> new AlreadyExistsException("User not found!"));
    if (!passwordEncoder.matches(request.password(), userEntity.getPassword())) {
      throw new AlreadyExistsException("Invalid credentials!");
    }
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.username(),
            request.password()
        )
    );
    return new JwtResponse(
        jwtService.generateAccessToken(userEntity),
        jwtService.generateRefreshToken(userEntity
        ));
  }

  public JwtResponse refreshToken(RefreshTokenRequest request) {
    RefreshToken refreshToken = refreshTokenService.findByToken(request.refreshToken())
        .orElseThrow(() -> new NotFoundException("Refresh token not found!"));
    refreshTokenService.verifyExpiration(refreshToken);

    User user = refreshToken.getUser();
    String newAccessToken = jwtService.generateAccessToken(user);
    return new JwtResponse(
        newAccessToken,
        refreshToken.getToken()
    );
  }
}