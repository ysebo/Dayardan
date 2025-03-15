package kg.alatoo.midterm_project.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import kg.alatoo.midterm_project.entity.Category;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.QuestionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class QuestionRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private QuestionRepository questionRepository;

  private Category category;

  @BeforeEach
  void setUp() {
    category = new Category();
    category.setName("Programming");
    entityManager.persist(category);
  }

  @AfterEach
  void tearDown() {
    entityManager.clear();
  }

  @Test
  void getRandomQuestions_ShouldReturnRandomQuestions() {
    Question question1 = new Question();
    question1.setTitle("What is Java?");
    question1.setDifficulty(Difficulty.EASY);
    question1.setType(QuestionType.MULTIPLE_CHOICE);
    question1.setCategory(category);
    entityManager.persist(question1);

    Question question2 = new Question();
    question2.setTitle("What is Spring Boot?");
    question2.setDifficulty(Difficulty.MEDIUM);
    question2.setType(QuestionType.MULTIPLE_CHOICE);
    question2.setCategory(category);
    entityManager.persist(question2);

    entityManager.flush();

    List<Question> randomQuestions = questionRepository.getRandomQuestions(1);

    assertEquals(1, randomQuestions.size());
  }

  @Test
  void getRandomQuestionsByCategory_ShouldReturnRandomQuestionsByCategory() {
    Category category2 = new Category();
    category2.setName("Database");
    entityManager.persist(category2);

    Question question1 = new Question();
    question1.setTitle("What is Java?");
    question1.setDifficulty(Difficulty.EASY);
    question1.setType(QuestionType.MULTIPLE_CHOICE);
    question1.setCategory(category);
    entityManager.persist(question1);

    Question question2 = new Question();
    question2.setTitle("What is SQL?");
    question2.setDifficulty(Difficulty.MEDIUM);
    question2.setType(QuestionType.MULTIPLE_CHOICE);
    question2.setCategory(category2);
    entityManager.persist(question2);

    entityManager.flush();

    List<Question> randomQuestions = questionRepository.getRandomQuestionsByCategory(category.getId(), 1);

    assertEquals(1, randomQuestions.size());
    assertEquals(category.getId(), randomQuestions.get(0).getCategory().getId());
  }
}