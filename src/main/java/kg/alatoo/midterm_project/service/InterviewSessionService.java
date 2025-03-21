package kg.alatoo.midterm_project.service;

import java.util.List;
import kg.alatoo.midterm_project.payload.request.InterviewRequest;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;

public interface InterviewSessionService {

  InterviewSessionResponse startSession(Long userId);

  List<InterviewSessionQuestionDTO> getSessionQuestions(Long sessionId);

  InterviewAnswerResponse submitAnswer(Long sessionId, InterviewRequest submission);
}
