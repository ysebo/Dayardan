package kg.alatoo.midterm_project.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.alatoo.midterm_project.config.OAuth2AuthenticationSuccessHandler;
import kg.alatoo.midterm_project.controller.api.AuthController;
import kg.alatoo.midterm_project.filter.JwtAuthenticationFilter;
import kg.alatoo.midterm_project.payload.request.LoginRequest;
import kg.alatoo.midterm_project.payload.request.RefreshTokenRequest;
import kg.alatoo.midterm_project.payload.request.UserRequest;
import kg.alatoo.midterm_project.payload.response.JwtResponse;
import kg.alatoo.midterm_project.payload.response.UserResponse;
import kg.alatoo.midterm_project.repository.UserRepository;
import kg.alatoo.midterm_project.security.SecurityConfig;
import kg.alatoo.midterm_project.service.AuthService;
import kg.alatoo.midterm_project.service.RefreshTokenService;
import kg.alatoo.midterm_project.service.impl.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({TestSecurityConfig.class})
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthService authService;
  @MockBean
  private RefreshTokenService refreshTokenService;
  @MockBean
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @MockBean
  private UserRepository userRepository;
  @MockBean
  private JwtService jwtService;
  private UserRequest userRequest;
  private UserResponse userResponse;
  private LoginRequest loginRequest;
  private JwtResponse jwtResponse;
  private RefreshTokenRequest refreshTokenRequest;

  @BeforeEach
  void setUp() {
    userRequest = new UserRequest("testuser", "test@example.com", "password");
    userResponse = new UserResponse(1L, "testuser", "test@example.com");
    loginRequest = new LoginRequest("testuser", "password");
    jwtResponse = new JwtResponse("accessToken", "refreshToken");
    refreshTokenRequest = new RefreshTokenRequest("refreshToken");
  }

  @Test
  @WithMockUser(username = "testuser", roles = {"ADMIN"})
  void register_ShouldReturnCreatedStatus() throws Exception {
    when(authService.register(any(UserRequest.class))).thenReturn(userResponse);

    mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "username": "testuser",
                        "email": "test@example.com",
                        "password": "password"
                    }
                    """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.username").value("testuser"))
        .andExpect(jsonPath("$.email").value("test@example.com"));
  }

  @Test
  @WithMockUser(username = "testuser", roles = {"ADMIN"})
  void login_ShouldReturnJwtResponse() throws Exception {
    when(authService.login(any(LoginRequest.class))).thenReturn(jwtResponse);

    mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "username": "testuser",
                        "password": "password"
                    }
                    """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").value("accessToken"))
        .andExpect(jsonPath("$.refreshToken").value("refreshToken"));
  }

  @Test
  @WithMockUser(username = "testuser", roles = {"ADMIN"})
  void refreshToken_ShouldReturnNewJwtResponse() throws Exception {
    when(authService.refreshToken(any(RefreshTokenRequest.class))).thenReturn(jwtResponse);

    mockMvc.perform(post("/api/auth/refresh-token")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "refreshToken": "refreshToken"
                    }
                    """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").value("accessToken"))
        .andExpect(jsonPath("$.refreshToken").value("refreshToken"));
  }

  @Test
  @WithMockUser(username = "testuser", roles = {"ADMIN"})
  void oauth2Success_ShouldReturnJwtResponse() throws Exception {
    mockMvc.perform(get("/api/auth/oauth2/success")
            .param("token", "accessToken")
            .param("refreshToken", "refreshToken"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").value("accessToken"))
        .andExpect(jsonPath("$.refreshToken").value("refreshToken"));
  }
}