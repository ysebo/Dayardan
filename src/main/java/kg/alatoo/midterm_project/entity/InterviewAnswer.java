package kg.alatoo.midterm_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "interview_answers")
@Setter
@Getter
public class InterviewAnswer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "session_question_id", nullable = false)
  @NotNull(message = "SessionQuestion cannot be null")
  private SessionQuestion sessionQuestion;
  @ManyToOne
  @JoinColumn(name = "answer_id")
  private Answer selectedAnswer;
  @Column(length = 500)
  private String userAnswer;
  @Column(nullable = false)
  private boolean isCorrect;
}