package kg.alatoo.midterm_project.controller.api;

import jakarta.validation.Valid;
import java.util.List;
import kg.alatoo.midterm_project.payload.request.QuestionRequest;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;
import kg.alatoo.midterm_project.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;


  @GetMapping
  public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
    return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
    return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<QuestionResponse> createQuestion(
      @Valid @RequestBody QuestionRequest questionRequest) {
    return new ResponseEntity<>(questionService.createQuestion(questionRequest),
        HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id,
      @Valid @RequestBody QuestionRequest questionRequest) {
    return new ResponseEntity<>(questionService.updateQuestion(id, questionRequest), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
    questionService.deleteQuestion(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
