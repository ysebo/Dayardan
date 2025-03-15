package kg.alatoo.midterm_project.repository;

import kg.alatoo.midterm_project.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
