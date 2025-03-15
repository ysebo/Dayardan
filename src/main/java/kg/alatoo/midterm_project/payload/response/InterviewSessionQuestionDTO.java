package kg.alatoo.midterm_project.payload.response;


public record InterviewSessionQuestionDTO(
    Long id,
    Long sessionId,
    QuestionResponse question
) {

}
