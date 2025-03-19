package kg.alatoo.midterm_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String content;
  @Column(nullable = false)
  private boolean isCorrect;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  public Answer() {
  }

  public Answer(Long id, String content, boolean isCorrect) {
    this.id = id;
    this.content = content;
    this.isCorrect = isCorrect;
  }
}
