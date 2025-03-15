package kg.alatoo.midterm_project.service.impl;

import java.util.List;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.mapper.QuestionMapper;
import kg.alatoo.midterm_project.payload.request.QuestionRequest;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;
import kg.alatoo.midterm_project.repository.QuestionRepository;
import kg.alatoo.midterm_project.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;


  public List<QuestionResponse> getAllQuestions() {
    return questionRepository.findAll().stream().map(questionMapper::toQuestionResponse).toList();
  }

  public QuestionResponse getQuestionById(Long id) {
    return questionRepository.findById(id).
        map(questionMapper::toQuestionResponse).
        orElseThrow(() -> new NotFoundException("Question not found"));
  }

  public QuestionResponse createQuestion(QuestionRequest request) {
    if(request.answers().size() < 2) {
      throw new NotFoundException("Question must have at least 2 answers");
    }
    return questionMapper.toQuestionResponse(questionRepository.save(questionMapper.toQuestion(request)));
  }

  public QuestionResponse updateQuestion(Long id, QuestionRequest request) {
    Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
    question.setTitle(request.title());
    question.setDescription(request.description());
    question.setDifficulty(request.difficulty());
    question.setType(request.type());
    question.setCorrectAnswer(request.correctAnswer());
    question.setAnswers(request.answers());
    return questionMapper.toQuestionResponse(questionRepository.save(question));
  }

  public void deleteQuestion(Long id) {
    questionRepository.deleteById(id);
  }

  public List<QuestionResponse> getRandomQuestions(int count) {
    return null;
  }
}
