package kg.alatoo.midterm_project.payload.request;

import jakarta.validation.constraints.NotNull;

public record AnswerRequest(
    @NotNull(message = "Content cannot be null")
    String content,
    @NotNull(message = "Correct cannot be null")
    boolean correct
) {}