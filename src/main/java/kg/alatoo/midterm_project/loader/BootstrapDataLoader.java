package kg.alatoo.midterm_project.loader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import kg.alatoo.midterm_project.entity.Answer;
import kg.alatoo.midterm_project.entity.Category;
import kg.alatoo.midterm_project.entity.Question;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.entity.UserRole;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.QuestionType;
import kg.alatoo.midterm_project.exceptions.NotLegalArgumentException;
import kg.alatoo.midterm_project.repository.AnswerRepository;
import kg.alatoo.midterm_project.repository.CategoryRepository;
import kg.alatoo.midterm_project.repository.QuestionRepository;
import kg.alatoo.midterm_project.repository.RoleRepository;
import kg.alatoo.midterm_project.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class BootstrapDataLoader implements CommandLineRunner {

  private final RoleRepository roleRepository;

  private final CategoryRepository categoryRepository;

  private final QuestionRepository questionRepository;

  private final AnswerRepository answerRepository;

  private final UserRepository userRepository;

  public BootstrapDataLoader(RoleRepository roleRepository, CategoryRepository categoryRepository,
      QuestionRepository questionRepository, AnswerRepository answerRepository,
      UserRepository userRepository) {
    this.roleRepository = roleRepository;
    this.categoryRepository = categoryRepository;
    this.questionRepository = questionRepository;
    this.answerRepository = answerRepository;
    this.userRepository = userRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    loadRoles();
    loadCategories();
    loadQuestions();
    loadAnswers();
    loadUsers();
  }

  private void loadRoles() throws IOException, CsvValidationException {
    try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/data/roles.csv"))) {
      String[] line;
      reader.readNext();
      while ((line = reader.readNext()) != null) {
        UserRole role = new UserRole();
        role.setName(line[1]);
        roleRepository.save(role);
      }
    }
  }

  private void loadCategories() throws IOException, CsvValidationException {
    try (CSVReader reader = new CSVReader(
        new FileReader("src/main/resources/data/categories.csv"))) {
      String[] line;
      reader.readNext();
      while ((line = reader.readNext()) != null) {
        Category category = new Category();
        category.setName(line[1]);
        categoryRepository.save(category);
      }
    }
  }



  private void loadQuestions() throws IOException, CsvValidationException {
    try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/data/questions.csv"))) {
      String[] line;
      reader.readNext();
      while ((line = reader.readNext()) != null) {
        try {
          long categoryId = Long.parseLong(line[2].trim());

          Category category = categoryRepository.findById(categoryId)
              .orElseThrow(() -> new RuntimeException("Category not found for ID: " + categoryId));

          Question question = new Question();
          question.setTitle(line[1].trim());
          question.setCategory(category);
          question.setDescription(line[3].trim());
          question.setDifficulty(Difficulty.valueOf(line[4].trim().toUpperCase()));
          question.setCorrectAnswer(line[5].trim());
          question.setType(QuestionType.valueOf(line[6].trim().toUpperCase()));

          questionRepository.save(question);
        } catch (NumberFormatException e) {
          System.err.println("Invalid categoryId in CSV: " + line[2]);
        } catch (IllegalArgumentException e) {
          System.err.println("Invalid enum value in CSV: " + e.getMessage());
        }
      }
    }
  }

  private void loadAnswers() throws IOException, CsvValidationException {
    try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/data/answers.csv"))) {
      String[] line;
      reader.readNext();
      while ((line = reader.readNext()) != null) {
        Answer answer = new Answer();
        Optional<Question> questionOptional = questionRepository.findById(Long.parseLong(line[1]));
        if (questionOptional.isEmpty()) {
          System.err.println("Question not found for ID: " + line[1]);
          continue;
        }
        answer.setQuestion(questionOptional.get());

        answer.setContent(line[2]);
        answer.setCorrect(Boolean.parseBoolean(line[3]));
        answerRepository.save(answer);
      }
    }
  }

  private void loadUsers() throws IOException, CsvValidationException {
    try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/data/users.csv"))) {
      String[] line;
      reader.readNext();

      while ((line = reader.readNext()) != null) {
        User user = new User();
        user.setUsername(line[1]);
        user.setEmail(line[2]);
        user.setPassword(line[3]);

        Optional<UserRole> roleOptional = roleRepository.findById(Long.parseLong(line[4]));
        if (roleOptional.isEmpty()) {
          System.err.println("Role not found for ID: " + line[4]);
          continue;
        }
        user.setUserRole(roleOptional.get());

        userRepository.save(user);
      }
    }
  }
}