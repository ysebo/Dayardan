package kg.alatoo.midterm_project.mapper;

import kg.alatoo.midterm_project.entity.InterviewAnswer;
import kg.alatoo.midterm_project.entity.InterviewSession;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.entity.SessionQuestion;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.QuestionType;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class InterviewMapperTest {

  @Mock
  private QuestionMapper questionMapper;

  @InjectMocks
  private InterviewMapper interviewMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void toModel_InterviewSession_ShouldReturnInterviewSessionResponse() {
    User user = new User();
    user.setId(123L);

    InterviewSession session = new InterviewSession();
    session.setId(1L);
    session.setUser(user);
    session.setStartedAt(LocalDateTime.now());

    InterviewSessionResponse response = interviewMapper.toModel(session);

    assertEquals(session.getId(), response.id());
    assertEquals(session.getUser().getId(), response.userId());
    assertEquals(session.getStartedAt(), response.startedAt());
  }

  @Test
  void toModel_SessionQuestion_ShouldReturnInterviewSessionQuestionDTO() {
    SessionQuestion sessionQuestion = new SessionQuestion();
    sessionQuestion.setId(1L);

    QuestionResponse questionResponse = new QuestionResponse(
        101L, "What is Java?", 1L, "A programming language",
        Difficulty.EASY, "Java is a programming language.",
        QuestionType.MULTIPLE_CHOICE, Collections.emptyList()
    );

    when(questionMapper.toQuestionResponse(sessionQuestion.getQuestion())).thenReturn(questionResponse);

    InterviewSessionQuestionDTO dto = interviewMapper.toModel(sessionQuestion);

    assertEquals(sessionQuestion.getId(), dto.sessionQuestionId());
    assertEquals(questionResponse, dto.question());
  }

  @Test
  void toModel_InterviewAnswer_ShouldReturnInterviewAnswerResponse() {
    Question question = new Question();
    question.setId(101L);

    SessionQuestion sessionQuestion = new SessionQuestion();
    sessionQuestion.setId(1L);
    sessionQuestion.setQuestion(question);

    InterviewSession session = new InterviewSession();
    session.setId(2L);

    sessionQuestion.setSession(session);
    InterviewAnswer answer = new InterviewAnswer();
    answer.setId(3L);
    answer.setSessionQuestion(sessionQuestion);
    answer.setUserAnswer("Java is a programming language.");
    answer.setCorrect(true);

    InterviewAnswerResponse response = interviewMapper.toModel(answer);

    assertEquals(answer.getId(), response.id());
    assertEquals(sessionQuestion.getSession().getId(), response.sessionId());
    assertEquals(sessionQuestion.getQuestion().getId(), response.questionId());
    assertEquals(answer.getUserAnswer(), response.answer());
    assertEquals(answer.isCorrect(), response.isCorrect());
  }
}
