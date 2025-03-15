package kg.alatoo.midterm_project.exceptions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void notFoundException_ShouldReturnNotFoundStatus() throws Exception {
    mockMvc.perform(get("/api/nonexistent-endpoint"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value("NOT_FOUND"))
        .andExpect(jsonPath("$.message").value("Resource not found"));
  }

  @Test
  void notLegalArgumentException_ShouldReturnBadRequestStatus() throws Exception {
    mockMvc.perform(get("/api/illegal-argument"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("Illegal argument provided"));
  }

  @Test
  void methodArgumentNotValidException_ShouldReturnBadRequestStatus() throws Exception {
    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"username\": \"\", \"password\": \"\", \"email\": \"invalid-email\" }"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("Username cannot be blank"));
  }
}