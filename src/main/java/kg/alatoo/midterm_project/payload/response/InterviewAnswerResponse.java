package kg.alatoo.midterm_project.payload.response;

public record InterviewAnswerResponse(
    Long id,
    Long sessionId,
    String answer,
    boolean isCorrect
) {

}
