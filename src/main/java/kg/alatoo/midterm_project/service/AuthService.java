package kg.alatoo.midterm_project.service;

import io.jsonwebtoken.Jwt;
import kg.alatoo.midterm_project.payload.request.LoginRequest;
import kg.alatoo.midterm_project.payload.request.RefreshTokenRequest;
import kg.alatoo.midterm_project.payload.request.UserRequest;
import kg.alatoo.midterm_project.payload.response.JwtResponse;
import kg.alatoo.midterm_project.payload.response.UserResponse;

public interface AuthService {
  UserResponse register(UserRequest userRequest);

  JwtResponse login(LoginRequest loginRequest);
  JwtResponse  refreshToken(RefreshTokenRequest refreshTokenRequest);

}
