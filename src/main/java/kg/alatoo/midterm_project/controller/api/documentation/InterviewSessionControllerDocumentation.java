package kg.alatoo.midterm_project.controller.api.documentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.alatoo.midterm_project.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import kg.alatoo.midterm_project.payload.request.InterviewRequest;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/interviews")
@Tag(name = "Interview Session Controller", description = "APIs for managing interview sessions")
public interface InterviewSessionControllerDocumentation {

  @PostMapping("/start/{userId}")
  @Operation(summary = "Start an interview session", description = "Start a new interview session for a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully started the interview session"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<InterviewSessionResponse> startInterview(@AuthenticationPrincipal User user);

  @GetMapping("/questions/{sessionId}")
  @Operation(summary = "Get interview questions", description = "Get questions for a specific interview session")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved interview questions"),
      @ApiResponse(responseCode = "404", description = "Interview session not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<List<InterviewSessionQuestionDTO>> getInterviewQuestions(@PathVariable Long sessionId);

  @PostMapping("/answer/{sessionId}")
  @Operation(summary = "Submit an answer", description = "Submit an answer for a specific interview session")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully submitted the answer"),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "404", description = "Interview session or question not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<InterviewAnswerResponse> submitAnswer(
      @PathVariable Long sessionId,
      @Valid @RequestBody InterviewRequest submission
  );
}