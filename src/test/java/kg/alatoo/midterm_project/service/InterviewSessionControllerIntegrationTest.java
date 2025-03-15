package kg.alatoo.midterm_project.service;
import java.util.ArrayList;
import kg.alatoo.midterm_project.entity.Answer;
import kg.alatoo.midterm_project.entity.Category;
import kg.alatoo.midterm_project.entity.InterviewSession;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.entity.SessionQuestion;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.InterviewStatus;
import kg.alatoo.midterm_project.enums.QuestionType;
import kg.alatoo.midterm_project.repository.AnswerRepository;
import kg.alatoo.midterm_project.repository.CategoryRepository;
import kg.alatoo.midterm_project.repository.InterviewSessionRepository;
import kg.alatoo.midterm_project.repository.QuestionRepository;
import kg.alatoo.midterm_project.repository.SessionQuestionRepository;
import kg.alatoo.midterm_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class InterviewSessionControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private InterviewSessionRepository interviewSessionRepository;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private SessionQuestionRepository sessionQuestionRepository;
  @Autowired
  private AnswerRepository answerRepository;

  private User user;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setUsername("eaitkaziev");
    user.setPassword("password");
    user.setEmail("erdan@gmail.com");
    userRepository.save(user);
  }

  @Test
  void startInterview_ShouldReturnCreatedSession() throws Exception {
    mockMvc.perform(post("/api/interviews/start/{userId}", user.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.userId").value(user.getId()))
        .andExpect(jsonPath("$.startedAt").exists());
  }
  @Test
  void getInterviewQuestions_ShouldReturnQuestions() throws Exception {
    InterviewSession session = new InterviewSession();
    session.setUser(user);
    session.setStartedAt(LocalDateTime.now());
    session.setSessionQuestions(new ArrayList<>());
    session.setScore(900);
    session.setStatus(InterviewStatus.SCHEDULED);
    interviewSessionRepository.save(session);

    Category category = new Category();
    category.setName("Programming");
    categoryRepository.save(category);

    Question question = new Question();
    question.setTitle("What is Java?");
    question.setCategory(category);
    question.setDifficulty(Difficulty.EASY);
    question.setType(QuestionType.MULTIPLE_CHOICE);
    question.setAnswers(new ArrayList<>());
    questionRepository.save(question);

    Answer answer = new Answer();
    answer.setContent("Java is a programming language.");
    answer.setCorrect(true);
    answer.setQuestion(question);
    answerRepository.save(answer);

    question.getAnswers().add(answer);
    questionRepository.save(question);

    SessionQuestion sessionQuestion = new SessionQuestion();
    sessionQuestion.setSession(session);
    sessionQuestion.setQuestion(question);
    sessionQuestionRepository.save(sessionQuestion);

    session.getSessionQuestions().add(sessionQuestion);
    interviewSessionRepository.save(session);

    mockMvc.perform(get("/api/interviews/questions/{sessionId}", session.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].question.id").value(question.getId())) // Updated JSON path
        .andExpect(jsonPath("$[0].question.title").value("What is Java?")) // Updated JSON path
        .andExpect(jsonPath("$[0].question.answers[0].content").value("Java is a programming language.")); // Updated JSON path
  }
}