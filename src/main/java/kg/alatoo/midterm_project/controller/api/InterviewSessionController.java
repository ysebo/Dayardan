package kg.alatoo.midterm_project.controller.api;

import jakarta.validation.Valid;
import java.util.List;
import kg.alatoo.midterm_project.controller.api.documentation.InterviewSessionControllerDocumentation;
import kg.alatoo.midterm_project.payload.request.InterviewRequest;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;
import kg.alatoo.midterm_project.service.InterviewSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterviewSessionController implements InterviewSessionControllerDocumentation {

  private final InterviewSessionService interviewSessionService;

  public ResponseEntity<InterviewSessionResponse> startInterview(@PathVariable Long userId) {
    return new ResponseEntity<>(interviewSessionService.startSession(userId), HttpStatus.CREATED);
  }

  public ResponseEntity<List<InterviewSessionQuestionDTO>> getInterviewQuestions(@PathVariable Long sessionId) {
    return new ResponseEntity<>(interviewSessionService.getSessionQuestions(sessionId), HttpStatus.OK);
  }

  public ResponseEntity<InterviewAnswerResponse> submitAnswer(
      @PathVariable Long sessionId,
      @Valid @RequestBody InterviewRequest submission
  ) {
    return new ResponseEntity<>(interviewSessionService.submitAnswer(sessionId, submission), HttpStatus.CREATED);
  }
}