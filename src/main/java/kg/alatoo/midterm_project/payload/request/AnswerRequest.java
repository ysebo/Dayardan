package kg.alatoo.midterm_project.payload.request;

public record AnswerRequest(
    String content,
    boolean correct
) {}