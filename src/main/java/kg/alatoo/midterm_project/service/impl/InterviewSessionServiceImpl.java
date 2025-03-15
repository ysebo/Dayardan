package kg.alatoo.midterm_project.service.impl;

import java.util.List;
import kg.alatoo.midterm_project.payload.request.InterviewRequest;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;
import kg.alatoo.midterm_project.repository.InterviewSessionRepository;
import kg.alatoo.midterm_project.repository.QuestionRepository;
import kg.alatoo.midterm_project.service.InterviewSessionService;
import org.springframework.stereotype.Service;

@Service
public class InterviewSessionServiceImpl implements InterviewSessionService {
  private final InterviewSessionRepository interviewSessionRepository;
  private final QuestionRepository questionRepository;

  public InterviewSessionServiceImpl(InterviewSessionRepository interviewSessionRepository,
      QuestionRepository questionRepository) {
    this.interviewSessionRepository = interviewSessionRepository;
    this.questionRepository = questionRepository;
  }

  public InterviewSessionResponse startInterview(Long userId) {
    return null;
  }

  public List<InterviewSessionQuestionDTO> getInterviewQuestions(Long sessionId) {
    return List.of();
  }

  public InterviewAnswerResponse submitAnswer(Long sessionId, InterviewRequest submission) {
    return null;
  }
}
