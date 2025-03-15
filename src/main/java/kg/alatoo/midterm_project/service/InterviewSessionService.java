package kg.alatoo.midterm_project.service;

import jakarta.validation.Valid;
import java.util.List;
import kg.alatoo.midterm_project.payload.request.InterviewRequest;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;

public interface InterviewSessionService {

  InterviewSessionResponse startInterview(Long userId);

  List<InterviewSessionQuestionDTO> getInterviewQuestions(Long sessionId);

  InterviewAnswerResponse submitAnswer(Long sessionId, InterviewRequest submission);
}
