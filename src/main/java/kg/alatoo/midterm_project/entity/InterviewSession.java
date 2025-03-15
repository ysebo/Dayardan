package kg.alatoo.midterm_project.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import kg.alatoo.midterm_project.enums.InterviewStatus;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "interview_sessions")
public class InterviewSession {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;
  private Integer score;
  @Enumerated(EnumType.STRING)
  private InterviewStatus status;
  @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
  private List<SessionQuestion> sessionQuestions;
}
