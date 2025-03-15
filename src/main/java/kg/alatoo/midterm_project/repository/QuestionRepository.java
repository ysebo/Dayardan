package kg.alatoo.midterm_project.repository;

import java.util.List;
import kg.alatoo.midterm_project.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
  @Query(value = "SELECT * FROM questions ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
  List<Question> getRandomQuestions(@Param("count") int count);
}
