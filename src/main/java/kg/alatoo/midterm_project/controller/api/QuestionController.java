package kg.alatoo.midterm_project.controller.api;

import jakarta.validation.Valid;
import java.util.List;
import kg.alatoo.midterm_project.controller.api.documentation.QuestionControllerDocumentation;
import kg.alatoo.midterm_project.payload.request.QuestionRequest;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;
import kg.alatoo.midterm_project.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController implements QuestionControllerDocumentation {

  private final QuestionService questionService;

  public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
    return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
  }

  public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
    return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
  }

  public ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody QuestionRequest questionRequest) {
    return new ResponseEntity<>(questionService.createQuestion(questionRequest), HttpStatus.CREATED);
  }

  public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id, @Valid @RequestBody QuestionRequest questionRequest) {
    return new ResponseEntity<>(questionService.updateQuestion(id, questionRequest), HttpStatus.OK);
  }

  public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
    questionService.deleteQuestion(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}