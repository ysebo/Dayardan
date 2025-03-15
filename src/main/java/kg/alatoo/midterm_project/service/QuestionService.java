package kg.alatoo.midterm_project.service;

import java.util.List;
import kg.alatoo.midterm_project.payload.request.QuestionRequest;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;

public interface QuestionService {

  List<QuestionResponse> getAllQuestions();

  QuestionResponse getQuestionById(Long id);

  QuestionResponse createQuestion(QuestionRequest request);

  QuestionResponse updateQuestion(Long id, QuestionRequest request);

  void deleteQuestion(Long id);

}
