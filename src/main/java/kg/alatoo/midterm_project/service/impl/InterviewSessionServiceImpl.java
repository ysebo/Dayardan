package kg.alatoo.midterm_project.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kg.alatoo.midterm_project.entity.InterviewAnswer;
import kg.alatoo.midterm_project.entity.InterviewSession;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.entity.SessionQuestion;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.mapper.InterviewMapper;
import kg.alatoo.midterm_project.payload.request.InterviewRequest;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;
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


  public InterviewSessionResponse startSession(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));

    InterviewSession session = new InterviewSession();
    session.setUser(user);
    session.setStartedAt(LocalDateTime.now());

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
    SessionQuestion sessionQuestion = sessionQuestionRepository.findById(submission.sessionId())
        .orElseThrow(() -> new NotFoundException("Session Question not found"));

    if (!sessionQuestion.getSession().getId().equals(sessionId)) {
      throw new IllegalArgumentException("Question does not belong to this session");
    }

    InterviewAnswer answer = new InterviewAnswer();
    answer.setSessionQuestion(sessionQuestion);
    answer.setUserAnswer(submission.answer());

    boolean isCorrect = sessionQuestion.getQuestion().getCorrectAnswer().equalsIgnoreCase(submission.answer());
    answer.setCorrect(isCorrect);

    interviewAnswerRepository.save(answer);

    return interviewMapper.toModel(answer);
  }
}
