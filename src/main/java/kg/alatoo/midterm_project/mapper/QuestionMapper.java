package kg.alatoo.midterm_project.mapper;

import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.payload.request.QuestionRequest;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {
    public QuestionResponse toQuestionResponse(Question question) {
        return new QuestionResponse(
            question.getId(),
            question.getTitle(),
            question.getCategory().getId(),
            question.getDescription(),
            question.getDifficulty(),
            question.getCorrectAnswer(),
            question.getType(),
            question.getAnswers()
        );
    }

    public Question toQuestion(QuestionRequest request) {
        return new Question(
            request.title(),
            request.description(),
            request.difficulty(),
            request.type(),
            request.correctAnswer(),
            null,
            request.answers()
        );
    }

}
