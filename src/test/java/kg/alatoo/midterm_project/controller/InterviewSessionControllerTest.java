package kg.alatoo.midterm_project.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import kg.alatoo.midterm_project.controller.api.InterviewSessionController;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.QuestionType;
import kg.alatoo.midterm_project.payload.request.InterviewRequest;
import kg.alatoo.midterm_project.payload.response.AnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewAnswerResponse;
import kg.alatoo.midterm_project.payload.response.InterviewSessionQuestionDTO;
import kg.alatoo.midterm_project.payload.response.InterviewSessionResponse;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;
import kg.alatoo.midterm_project.service.InterviewSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(InterviewSessionController.class)
class InterviewSessionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InterviewSessionService interviewSessionService;

  @Autowired
  private ObjectMapper objectMapper;

  private InterviewSessionResponse sessionResponse;
  private InterviewSessionQuestionDTO question1;
  private InterviewSessionQuestionDTO question2;
  private InterviewAnswerResponse answerResponse;

  @BeforeEach
  void setUp() {
    sessionResponse = new InterviewSessionResponse(1L, 123L, LocalDateTime.now());

    QuestionResponse questionResponse1 = new QuestionResponse(
        101L,
        "What is Java?",
        1L,
        "A programming language",
        Difficulty.EASY,
        "Java is a programming language.",
        QuestionType.MULTIPLE_CHOICE,
        Arrays.asList(
            new AnswerResponse(1L, "Java is a scripting language.", false),
            new AnswerResponse(2L, "Java is a programming language.", true)
        )
    );

    QuestionResponse questionResponse2 = new QuestionResponse(
        102L,
        "What is Spring Boot?",
        2L,
        "A framework for building Java applications",
        Difficulty.MEDIUM,
        "Spring Boot is a framework.",
        QuestionType.MULTIPLE_CHOICE,
        Arrays.asList(
            new AnswerResponse(3L, "Spring Boot is a programming language.", false),
            new AnswerResponse(4L, "Spring Boot is a framework.", true)
        )
    );

    question1 = new InterviewSessionQuestionDTO(1L, questionResponse1);
    question2 = new InterviewSessionQuestionDTO(2L, questionResponse2);

    answerResponse = new InterviewAnswerResponse(1L, 1L, 101L, "Java is a programming language.", true);
  }

  @Test
  @WithMockUser(username = "testuser", roles = {"ADMIN"})

  void startInterview_ShouldReturnCreatedSession() throws Exception {
    User user1 = new User();
    user1.setUsername("test");
    user1.setPassword("test");
    when(interviewSessionService.startSession(user1)).thenReturn(sessionResponse);

    mockMvc.perform(post("/api/interviews/start/123"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.userId").value(123))
        .andExpect(jsonPath("$.startedAt").exists());
  }

  @Test
  @WithMockUser(username = "testuser", roles = {"ADMIN"})

  void getInterviewQuestions_ShouldReturnListOfQuestions() throws Exception {
    List<InterviewSessionQuestionDTO> questions = Arrays.asList(question1, question2);
    when(interviewSessionService.getSessionQuestions(anyLong())).thenReturn(questions);

    mockMvc.perform(get("/api/interviews/questions/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2))
        .andExpect(jsonPath("$[0].sessionQuestionId").value(1))
        .andExpect(jsonPath("$[0].question.id").value(101))
        .andExpect(jsonPath("$[0].question.title").value("What is Java?"))
        .andExpect(jsonPath("$[0].question.categoryId").value(1))
        .andExpect(jsonPath("$[0].question.description").value("A programming language"))
        .andExpect(jsonPath("$[0].question.difficulty").value("EASY"))
        .andExpect(jsonPath("$[0].question.correctAnswer").value("Java is a programming language."))
        .andExpect(jsonPath("$[0].question.type").value("MULTIPLE_CHOICE"))
        .andExpect(jsonPath("$[0].question.answers.size()").value(2))
        .andExpect(jsonPath("$[0].question.answers[0].id").value(1))
        .andExpect(jsonPath("$[0].question.answers[0].content").value("Java is a scripting language."))
        .andExpect(jsonPath("$[0].question.answers[0].correct").value(false))
        .andExpect(jsonPath("$[0].question.answers[1].id").value(2))
        .andExpect(jsonPath("$[0].question.answers[1].content").value("Java is a programming language."))
        .andExpect(jsonPath("$[0].question.answers[1].correct").value(true))
        .andExpect(jsonPath("$[1].sessionQuestionId").value(2))
        .andExpect(jsonPath("$[1].question.id").value(102))
        .andExpect(jsonPath("$[1].question.title").value("What is Spring Boot?"))
        .andExpect(jsonPath("$[1].question.categoryId").value(2))
        .andExpect(jsonPath("$[1].question.description").value("A framework for building Java applications"))
        .andExpect(jsonPath("$[1].question.difficulty").value("MEDIUM"))
        .andExpect(jsonPath("$[1].question.correctAnswer").value("Spring Boot is a framework."))
        .andExpect(jsonPath("$[1].question.type").value("MULTIPLE_CHOICE"))
        .andExpect(jsonPath("$[1].question.answers.size()").value(2))
        .andExpect(jsonPath("$[1].question.answers[0].id").value(3))
        .andExpect(jsonPath("$[1].question.answers[0].content").value("Spring Boot is a programming language."))
        .andExpect(jsonPath("$[1].question.answers[0].correct").value(false))
        .andExpect(jsonPath("$[1].question.answers[1].id").value(4))
        .andExpect(jsonPath("$[1].question.answers[1].content").value("Spring Boot is a framework."))
        .andExpect(jsonPath("$[1].question.answers[1].correct").value(true));
  }

  @Test
  @WithMockUser(username = "testuser", roles = {"ADMIN"})

  void submitAnswer_ShouldReturnAnswerResponse() throws Exception {
    InterviewRequest request = new InterviewRequest(101L, 1L,"Java is a programming language.");
    when(interviewSessionService.submitAnswer(anyLong(), any(InterviewRequest.class)))
        .thenReturn(answerResponse);

    mockMvc.perform(post("/api/interviews/answer/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.sessionId").value(1))
        .andExpect(jsonPath("$.questionId").value(101))
        .andExpect(jsonPath("$.answer").value("Java is a programming language."))
        .andExpect(jsonPath("$.isCorrect").value(true));
  }
}