package kg.alatoo.midterm_project.payload.response;

import java.util.List;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.QuestionType;

public record QuestionResponse(
    Long id,
    String title,
    Long categoryId,
    String description,
    Difficulty difficulty,
    String correctAnswer,
    QuestionType type,
    List<AnswerResponse> answers
) {
}
