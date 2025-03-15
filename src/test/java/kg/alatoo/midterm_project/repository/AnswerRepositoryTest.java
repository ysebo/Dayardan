package kg.alatoo.midterm_project.repository;

import kg.alatoo.midterm_project.entity.Answer;
import kg.alatoo.midterm_project.entity.Category;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.QuestionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class AnswerRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AnswerRepository answerRepository;

  @AfterEach
  void tearDown() {
    entityManager.clear();
  }
  @Test
  void findById_ShouldReturnAnswer() {
    Category category = new Category();
    category.setName("Programming");
    entityManager.persist(category);
    Question question = new Question();
    question.setTitle("What is Java?");
    question.setDifficulty(Difficulty.EASY);
    question.setType(QuestionType.MULTIPLE_CHOICE);
    question.setDescription("Java is a programming language.");
    question.setCategory(category);
    entityManager.persist(question);

    Answer answer = new Answer();
    answer.setContent("Java is a programming language.");
    answer.setCorrect(true);
    answer.setQuestion(question);
    entityManager.persist(answer);
    entityManager.flush();

    Optional<Answer> foundAnswer = answerRepository.findById(answer.getId());

    assertTrue(foundAnswer.isPresent());
    assertEquals(answer.getContent(), foundAnswer.get().getContent());
  }
}