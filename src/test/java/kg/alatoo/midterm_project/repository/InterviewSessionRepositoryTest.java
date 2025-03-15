package kg.alatoo.midterm_project.repository;

import java.time.LocalDateTime;
import kg.alatoo.midterm_project.entity.InterviewSession;
import kg.alatoo.midterm_project.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class InterviewSessionRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private InterviewSessionRepository interviewSessionRepository;

  @AfterEach
  void tearDown() {
    entityManager.clear();
  }

  @Test
  void findByUserId_ShouldReturnSessionsForUser() {
    User user = new User();
    user.setUsername("user");
    user.setPassword("password");
    user.setEmail("erdan@gmail.com");
    entityManager.persist(user);

    InterviewSession session1 = new InterviewSession();
    session1.setUser(user);
    session1.setStartedAt(LocalDateTime.now());
    entityManager.persist(session1);

    InterviewSession session2 = new InterviewSession();
    session2.setUser(user);
    session2.setStartedAt(LocalDateTime.now());
    entityManager.persist(session2);

    entityManager.flush();

    List<InterviewSession> sessions = interviewSessionRepository.findByUserId(user.getId());

    assertEquals(2, sessions.size());
    assertEquals(user.getId(), sessions.get(0).getUser().getId());
  }
}