package kg.alatoo.midterm_project.controller.api.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.alatoo.midterm_project.payload.request.LoginRequest;
import kg.alatoo.midterm_project.payload.request.RefreshTokenRequest;
import kg.alatoo.midterm_project.payload.request.UserRequest;
import kg.alatoo.midterm_project.payload.response.JwtResponse;
import kg.alatoo.midterm_project.payload.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "Controller for user authentication and registration")
public interface AuthControllerDocumentation {

  @PostMapping("/register")
  @Operation(summary = "Register a new user", description = "Endpoint for user registration")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "User registered successfully"),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid input data"),
      @ApiResponse(
          responseCode = "409",
          description = "User already exists")

  })
  ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest);

  @PostMapping("/login")
  @Operation(summary = "User login", description = "Endpoint for user login")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "User logged in successfully"),
      @ApiResponse(
          responseCode = "401",
          description = "Invalid credentials"),
      @ApiResponse(
          responseCode = "404",
          description = "User not found")
  })
  ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest);


  @PostMapping("/refresh-token")
  @Operation(summary = "Refresh token", description = "Endpoint for refreshing token")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Token refreshed successfully"),
      @ApiResponse(
          responseCode = "401",
          description = "Invalid credentials"),
      @ApiResponse(
          responseCode = "404",
          description = "Token not found")
  })
  ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request);
  @GetMapping("/oauth2/success")
  @Operation(summary = "Success oauthLogin", description = "Endpoint for retrieving sucess message after login using OAuth2")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successful login"
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden")
  })
  ResponseEntity<JwtResponse> oauth2Success(
      @RequestParam String token,
      @RequestParam String refreshToken
  );
}
