package kg.alatoo.midterm_project.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "session_questions")
public class SessionQuestion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "session_id", nullable = false)
  private InterviewSession session;

  @ManyToOne
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;
  @OneToMany(mappedBy = "sessionQuestion", cascade = CascadeType.ALL)
  private List<InterviewAnswer> answers;

}
