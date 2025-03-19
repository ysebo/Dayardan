package kg.alatoo.midterm_project.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.QuestionType;

import java.util.List;

public record QuestionRequest(
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be less than 255 characters")
    String title,

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be a positive number")
    Long categoryId,

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    String description,

    @NotNull(message = "Difficulty is required")
    Difficulty difficulty,

    @NotBlank(message = "Correct answer is required")
    @Size(max = 500, message = "Correct answer must be less than 500 characters")
    String correctAnswer,

    @NotNull(message = "Question type is required")
    QuestionType type,

    @NotNull(message = "Answers list is required")
    @Size(min = 1, message = "At least one answer is required")
    List<AnswerRequest> answers
) {}
