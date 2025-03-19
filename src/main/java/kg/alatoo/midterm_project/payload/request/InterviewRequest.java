package kg.alatoo.midterm_project.payload.request;

import jakarta.validation.constraints.NotNull;

public record InterviewRequest(
    @NotNull(message = "Interview id is required") Long interviewId,
    Long questionId,
    @NotNull(message = "Answer is required")
    String answer
) {
}
