package kg.alatoo.midterm_project.payload.response;

public record AnswerResponse(
    Long id,
    String content,
    boolean correct
) {}
