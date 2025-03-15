package kg.alatoo.midterm_project.payload.response;


public record InterviewSessionQuestionDTO(
    Long sessionQuestionId,
    QuestionResponse question
) {

}
