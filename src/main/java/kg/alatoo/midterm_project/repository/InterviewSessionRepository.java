package kg.alatoo.midterm_project.repository;

import java.util.List;
import kg.alatoo.midterm_project.entity.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {
  @Query("SELECT i FROM InterviewSession i WHERE i.user.id = :userId")
  List<InterviewSession> findByUserId(Long userId);

}
