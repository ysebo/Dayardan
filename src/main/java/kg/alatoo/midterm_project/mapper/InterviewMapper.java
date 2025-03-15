package kg.alatoo.midterm_project.mapper;

import kg.alatoo.midterm_project.entity.InterviewAnswer;
import kg.alatoo.midterm_project.entity.InterviewSession;
import kg.alatoo.midterm_project.entity.SessionQuestion;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;
import org.springframework.stereotype.Component;

@Component
public class InterviewMapper {
  private final QuestionMapper questionMapper;

  public InterviewMapper(QuestionMapper questionMapper) {
    this.questionMapper = questionMapper;
  }

  public InterviewSessionResponse toModel(InterviewSession session) {
    return new InterviewSessionResponse(
        session.getId(),
        session.getUser().getId(),
        session.getStartedAt(),
        session.getFinishedAt()
    );
  }

  public InterviewSessionQuestionDTO toModel(SessionQuestion sessionQuestion) {
    return new InterviewSessionQuestionDTO(
        sessionQuestion.getId(),
        sessionQuestion.getSession().getId(),
        questionMapper.toQuestionResponse(sessionQuestion.getQuestion())
    );
  }


  public InterviewAnswerResponse toModel(InterviewAnswer answer) {
    return new InterviewAnswerResponse(
        answer.getId(),
        answer.getSessionQuestion().getSession().getId(),
        answer.getUserAnswer(),
        answer.isCorrect()
    );
  }
}
