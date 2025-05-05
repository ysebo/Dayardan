package kg.alatoo.midterm_project.controller.api;

import kg.alatoo.midterm_project.controller.api.documentation.AuthControllerDocumentation;
import kg.alatoo.midterm_project.payload.request.LoginRequest;
import kg.alatoo.midterm_project.payload.request.RefreshTokenRequest;
import kg.alatoo.midterm_project.payload.request.UserRequest;
import kg.alatoo.midterm_project.payload.response.JwtResponse;
import kg.alatoo.midterm_project.payload.response.UserResponse;
import kg.alatoo.midterm_project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocumentation {
  private final AuthService authService;
  public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
    return new ResponseEntity<>(authService.register(userRequest), HttpStatus.CREATED);
  }
  public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
    return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
  }

  public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
    return new ResponseEntity<>(authService.refreshToken(request),HttpStatus.OK);
  }
  public ResponseEntity<JwtResponse> oauth2Success(
      @RequestParam String token,
      @RequestParam String refreshToken
  ) {
    return new ResponseEntity<>(new JwtResponse(token, refreshToken), HttpStatus.OK);
  }


}
