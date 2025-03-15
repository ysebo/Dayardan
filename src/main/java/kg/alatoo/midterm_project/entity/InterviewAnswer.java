package kg.alatoo.midterm_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
  private SessionQuestion sessionQuestion;
  @ManyToOne
  @JoinColumn(name = "answer_id")
  private Answer selectedAnswer;
  private String userAnswer;
  private boolean isCorrect;
  private Integer timeSpentInSeconds;
}