package kg.alatoo.midterm_project.controller.api;

import jakarta.validation.Valid;
import java.util.List;
import kg.alatoo.midterm_project.payload.request.InterviewRequest;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;
import kg.alatoo.midterm_project.service.InterviewSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewSessionController {

  private final InterviewSessionService interviewSessionService;


  @PostMapping("/start/{userId}")
  public ResponseEntity<InterviewSessionResponse> startInterview(@PathVariable Long userId) {
    return new ResponseEntity<>(interviewSessionService.startSession(userId), HttpStatus.CREATED);
  }

  @GetMapping("/questions/{sessionId}")
  public ResponseEntity<List<InterviewSessionQuestionDTO>> getInterviewQuestions(
      @PathVariable Long sessionId) {
    return new ResponseEntity<>(interviewSessionService.getSessionQuestions(sessionId),
        HttpStatus.OK);
  }

  @PostMapping("/answer/{sessionId}")
  public ResponseEntity<InterviewAnswerResponse> submitAnswer(
      @PathVariable Long sessionId,
      @Valid @RequestBody InterviewRequest submission
  ) {
    return new ResponseEntity<>(interviewSessionService.submitAnswer(sessionId, submission),
        HttpStatus.CREATED);
  }
}
