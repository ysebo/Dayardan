package kg.alatoo.midterm_project.mapper;

import kg.alatoo.midterm_project.payload.request.AnswerRequest;
import kg.alatoo.midterm_project.payload.response.AnswerResponse;
import kg.alatoo.midterm_project.payload.request.QuestionRequest;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;
import kg.alatoo.midterm_project.entity.Answer;
import kg.alatoo.midterm_project.entity.Category;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.QuestionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionMapperTest {

  private QuestionMapper questionMapper;

  @BeforeEach
  void setUp() {
    questionMapper = new QuestionMapper();
  }

  @Test
  void toQuestionResponse_ShouldReturnQuestionResponse() {
    Question question = new Question();
    question.setId(101L);
    question.setTitle("What is Java?");
    question.setCategory(new Category(1L, "Programming"));
    question.setDescription("A programming language");
    question.setDifficulty(Difficulty.EASY);
    question.setCorrectAnswer("Java is a programming language.");
    question.setType(QuestionType.MULTIPLE_CHOICE);

    Answer answer1 = new Answer();
    answer1.setId(1L);
    answer1.setContent("Java is a scripting language.");
    answer1.setCorrect(false);

    Answer answer2 = new Answer();
    answer2.setId(2L);
    answer2.setContent("Java is a programming language.");
    answer2.setCorrect(true);

    question.setAnswers(Arrays.asList(answer1, answer2));

    QuestionResponse response = questionMapper.toQuestionResponse(question);

    assertEquals(question.getId(), response.id());
    assertEquals(question.getTitle(), response.title());
    assertEquals(question.getCategory().getId(), response.categoryId());
    assertEquals(question.getDescription(), response.description());
    assertEquals(question.getDifficulty(), response.difficulty());
    assertEquals(question.getCorrectAnswer(), response.correctAnswer());
    assertEquals(question.getType(), response.type());

    List<AnswerResponse> answerResponses = response.answers();
    assertEquals(2, answerResponses.size());
    assertEquals(answer1.getId(), answerResponses.get(0).id());
    assertEquals(answer1.getContent(), answerResponses.get(0).content());
    assertEquals(answer1.isCorrect(), answerResponses.get(0).correct());
    assertEquals(answer2.getId(), answerResponses.get(1).id());
    assertEquals(answer2.getContent(), answerResponses.get(1).content());
    assertEquals(answer2.isCorrect(), answerResponses.get(1).correct());
  }

  @Test
  void toQuestion_ShouldReturnUpdatedQuestion() {
    Question question = new Question();
    Category category = new Category(1L, "Programming");

    List<AnswerRequest> answers1 = Arrays.asList(
        new AnswerRequest( "Java is a scripting language.", false),
        new AnswerRequest( "Java is a programming language.", true)
    );

    QuestionRequest request = new QuestionRequest(
        "What is Java?",
        1L,
        "A programming language",
        Difficulty.EASY,
        "Java is a programming language.",
        QuestionType.MULTIPLE_CHOICE,
        answers1
    );

    Question updatedQuestion = questionMapper.toQuestion(question, request, category);

    assertEquals(request.title(), updatedQuestion.getTitle());
    assertEquals(request.description(), updatedQuestion.getDescription());
    assertEquals(request.difficulty(), updatedQuestion.getDifficulty());
    assertEquals(request.type(), updatedQuestion.getType());
    assertEquals(category, updatedQuestion.getCategory());

    List<Answer> answers = updatedQuestion.getAnswers();
    assertEquals(2, answers.size());

    assertEquals(1L, answers.get(0).getId());
    assertEquals("Java is a scripting language.", answers.get(0).getContent());
    assertEquals(false, answers.get(0).isCorrect());

    assertEquals(2L, answers.get(1).getId());
    assertEquals("Java is a programming language.", answers.get(1).getContent());
    assertEquals(true, answers.get(1).isCorrect());
  }
}