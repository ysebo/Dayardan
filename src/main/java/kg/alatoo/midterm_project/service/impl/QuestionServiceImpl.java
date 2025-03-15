package kg.alatoo.midterm_project.service.impl;

import java.util.ArrayList;
import java.util.List;
import kg.alatoo.midterm_project.entity.Answer;
import kg.alatoo.midterm_project.entity.Category;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.enums.QuestionType;
import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.exceptions.NotLegalArgumentException;
import kg.alatoo.midterm_project.mapper.QuestionMapper;
import kg.alatoo.midterm_project.payload.request.QuestionRequest;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;
import kg.alatoo.midterm_project.repository.AnswerRepository;
import kg.alatoo.midterm_project.repository.CategoryRepository;
import kg.alatoo.midterm_project.repository.QuestionRepository;
import kg.alatoo.midterm_project.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;
  private final CategoryRepository categoryRepository;
  private final AnswerRepository answerRepository;


  public List<QuestionResponse> getAllQuestions() {
    return questionRepository.findAll().stream().map(questionMapper::toQuestionResponse).toList();
  }

  public QuestionResponse getQuestionById(Long id) {
    return questionRepository.findById(id).
        map(questionMapper::toQuestionResponse).
        orElseThrow(() -> new NotFoundException("Question not found"));
  }

  public QuestionResponse createQuestion(QuestionRequest request) {
    if (request.type() == QuestionType.MULTIPLE_CHOICE &&
        (request.answers() == null || request.answers().size() < 2)) {
      throw new NotLegalArgumentException("Multiple choice questions must have at least 2 answers");
    }

    if (request.type() == QuestionType.MULTIPLE_CHOICE) {
      boolean hasCorrectAnswer = request.answers().stream()
          .anyMatch(answer -> answer.correct());
      if (!hasCorrectAnswer) {
        throw new NotLegalArgumentException("Multiple choice questions must have at least one correct answer");
      }
    }

    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new NotFoundException("Category not found"));

    Question question = new Question();
    Question mappedQuestion = questionMapper.toQuestion(question, request, category);
    Question savedQuestion = questionRepository.save(mappedQuestion);
    return questionMapper.toQuestionResponse(savedQuestion);
  }

  public QuestionResponse updateQuestion(Long id, QuestionRequest request) {
    Question question = questionRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Question not found"));

    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new NotFoundException("Category not found"));

    List<Answer> existingAnswers = new ArrayList<>(question.getAnswers());

    questionMapper.toQuestion(question, request, category);

    if (question.getType() == QuestionType.MULTIPLE_CHOICE) {
      for (Answer existingAnswer : existingAnswers) {
        boolean stillExists = question.getAnswers().stream()
            .anyMatch(newAnswer ->
                newAnswer.getId() != null && newAnswer.getId().equals(existingAnswer.getId()));

        if (!stillExists) {
          answerRepository.delete(existingAnswer);
        }
      }
    }

    Question savedQuestion = questionRepository.save(question);
    return questionMapper.toQuestionResponse(savedQuestion);
  }

  public void deleteQuestion(Long id) {
    questionRepository.deleteById(id);
  }
}
