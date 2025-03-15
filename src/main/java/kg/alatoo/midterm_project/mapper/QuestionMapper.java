package kg.alatoo.midterm_project.mapper;

import java.util.List;
import java.util.stream.Collectors;
import kg.alatoo.midterm_project.entity.Answer;
import kg.alatoo.midterm_project.entity.Category;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.enums.QuestionType;
import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.payload.request.QuestionRequest;
import kg.alatoo.midterm_project.payload.response.AnswerResponse;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;
import kg.alatoo.midterm_project.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {
    private final CategoryRepository categoryRepository;

  public QuestionMapper(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

    public QuestionResponse toQuestionResponse(Question question) {
        return new QuestionResponse(
            question.getId(),
            question.getTitle(),
            question.getCategory().getId(),
            question.getDescription(),
            question.getDifficulty(),
            question.getCorrectAnswer(),
            question.getType(),
            question.getAnswers().stream()
                .map(this::toAnswerResponse)
                .collect(Collectors.toList())
        );
    }

    public Question toQuestion(Question question, QuestionRequest request, Category category) {
        question.setTitle(request.title());
        question.setDescription(request.description());
        question.setDifficulty(request.difficulty());
        question.setType(request.type());
        question.setCategory(category);

        List<Answer> answers = request.answers().stream()
            .map(answerDto -> {
                Answer answer = new Answer();
                answer.setContent(answerDto.getContent());
                answer.setCorrect(answerDto.isCorrect());
                answer.setQuestion(question);
                return answer;
            }).collect(Collectors.toList());
      if (request.type() != QuestionType.MULTIPLE_CHOICE) {
        question.setCorrectAnswer(request.correctAnswer());
      }
        question.setAnswers(answers);

        return question;
    }

    private AnswerResponse toAnswerResponse(Answer answer) {
        return new AnswerResponse(
            answer.getId(),
            answer.getContent(),
            answer.isCorrect()
        );
    }



}
