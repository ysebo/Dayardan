package kg.alatoo.midterm_project.payload.request;

import java.util.List;
import kg.alatoo.midterm_project.entity.Answer;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.QuestionType;

public record QuestionRequest(
    String title,
    Long categoryId,
    String description,
    Difficulty difficulty,
    String correctAnswer,
    QuestionType type,
    List<Answer> answers
){
}
