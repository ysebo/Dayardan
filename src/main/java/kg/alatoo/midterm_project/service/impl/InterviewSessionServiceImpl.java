package kg.alatoo.midterm_project.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kg.alatoo.midterm_project.entity.Answer;
import kg.alatoo.midterm_project.entity.InterviewAnswer;
import kg.alatoo.midterm_project.entity.InterviewSession;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.entity.SessionQuestion;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.enums.InterviewStatus;
import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.exceptions.NotLegalArgumentException;
import kg.alatoo.midterm_project.mapper.InterviewMapper;
import kg.alatoo.midterm_project.payload.request.InterviewRequest;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;
import kg.alatoo.midterm_project.repository.AnswerRepository;
import kg.alatoo.midterm_project.repository.InterviewAnswerRepository;
import kg.alatoo.midterm_project.repository.InterviewSessionRepository;
import kg.alatoo.midterm_project.repository.QuestionRepository;
import kg.alatoo.midterm_project.repository.SessionQuestionRepository;
import kg.alatoo.midterm_project.repository.UserRepository;
import kg.alatoo.midterm_project.service.InterviewSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewSessionServiceImpl implements InterviewSessionService {

  private final InterviewSessionRepository interviewSessionRepository;
  private final QuestionRepository questionRepository;
  private final SessionQuestionRepository sessionQuestionRepository;
  private final UserRepository userRepository;
  private final InterviewAnswerRepository interviewAnswerRepository;
  private final InterviewMapper interviewMapper;
  private final AnswerRepository answerRepository;


  public InterviewSessionResponse startSession(User sesionUser) {
    User user = userRepository.findById(sesionUser.getId())
        .orElseThrow(() -> new NotFoundException("User not found"));

    InterviewSession session = new InterviewSession();
    session.setUser(user);
    session.setStartedAt(LocalDateTime.now());
    session.setStatus(InterviewStatus.IN_PROGRESS);

    interviewSessionRepository.save(session);

    List<Question> randomQuestions = questionRepository.getRandomQuestions(5);
    List<SessionQuestion> sessionQuestions = new ArrayList<>();
    for (Question question : randomQuestions) {
      SessionQuestion sessionQuestion = new SessionQuestion();
      sessionQuestion.setQuestion(question);
      sessionQuestion.setSession(session);
      sessionQuestions.add(sessionQuestion);
    }

    sessionQuestionRepository.saveAll(sessionQuestions);

    return interviewMapper.toModel(session);

  }

  public List<InterviewSessionQuestionDTO> getSessionQuestions(Long sessionId) {
    InterviewSession session = interviewSessionRepository.findById(sessionId)
        .orElseThrow(() -> new NotFoundException("Session not found"));

    return session.getSessionQuestions().stream()
        .map(interviewMapper::toModel)
        .collect(Collectors.toList());
  }

  public InterviewAnswerResponse submitAnswer(Long sessionId, InterviewRequest submission) {
    InterviewSession session = interviewSessionRepository.findById(sessionId)
        .orElseThrow(() -> new NotFoundException("Session not found"));

    SessionQuestion sessionQuestion = sessionQuestionRepository.findById(submission.questionId())
        .orElseThrow(() -> new NotFoundException("Session Question not found"));

    if (!sessionQuestion.getSession().getId().equals(sessionId)) {
      throw new NotLegalArgumentException("Question does not belong to this session");
    }

    Question question = sessionQuestion.getQuestion();
    InterviewAnswer answer = new InterviewAnswer();
    answer.setSessionQuestion(sessionQuestion);

    boolean isCorrect = false;

    switch (question.getType()) {
      case MULTIPLE_CHOICE:
        String submittedAnswer = submission.answer();
        Answer selectedAnswer = null;


        try {
          Long selectedAnswerId = Long.parseLong(submittedAnswer);
          selectedAnswer = answerRepository.findById(selectedAnswerId)
              .orElse(null);
        } catch (NumberFormatException e) {
          System.out.println(e);
        }

        if (selectedAnswer == null) {
          selectedAnswer = question.getAnswers().stream()
              .filter(a -> a.getContent().equals(submittedAnswer))
              .findFirst()
              .orElseThrow(() -> new NotLegalArgumentException("Invalid answer option: " + submittedAnswer));
        }


        if (!selectedAnswer.getQuestion().getId().equals(question.getId())) {
          throw new NotLegalArgumentException("Answer does not belong to this question");
        }

        answer.setSelectedAnswer(selectedAnswer);
        answer.setUserAnswer(selectedAnswer.getContent());
        isCorrect = selectedAnswer.isCorrect();
        break;

      case TRUE_FALSE:
        answer.setUserAnswer(submission.answer());
        isCorrect = question.getCorrectAnswer().equalsIgnoreCase(submission.answer());
        break;

      case SHORT_ANSWER:
      case CODING:
        answer.setUserAnswer(submission.answer());

        if (question.getCorrectAnswer() != null && !question.getCorrectAnswer().isEmpty()) {
          isCorrect = question.getCorrectAnswer().equalsIgnoreCase(submission.answer());
        } else {
          isCorrect = false;
        }
        break;
    }

    answer.setCorrect(isCorrect);

    InterviewAnswer savedAnswer = interviewAnswerRepository.save(answer);

    if (sessionQuestion.getAnswers() == null) {
      sessionQuestion.setAnswers(new ArrayList<>());
    }
    sessionQuestion.getAnswers().add(savedAnswer);
    sessionQuestionRepository.save(sessionQuestion);

    if (isCorrect) {
      if (session.getScore() == null) {
        session.setScore(1);
      } else {
        session.setScore(session.getScore() + 1);
      }
      interviewSessionRepository.save(session);
    }

    boolean allAnswered = session.getSessionQuestions().stream()
        .allMatch(sq -> sq.getAnswers() != null && !sq.getAnswers().isEmpty());

    if (allAnswered) {
      session.setStatus(InterviewStatus.COMPLETED);
      session.setFinishedAt(LocalDateTime.now());
      interviewSessionRepository.save(session);
    }

    return interviewMapper.toModel(savedAnswer);
  }
}
