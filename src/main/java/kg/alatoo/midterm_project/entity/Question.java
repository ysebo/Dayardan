package kg.alatoo.midterm_project.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import java.util.List;
import kg.alatoo.midterm_project.enums.Difficulty;
import kg.alatoo.midterm_project.enums.QuestionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String title;

  private String description;

  @Enumerated(EnumType.STRING)
  private Difficulty difficulty;
  @Enumerated(EnumType.STRING)
  private QuestionType type;

  private String correctAnswer;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private List<Answer> answers;

  public Question() {

  }

  public Question(String title, String description, Difficulty difficulty, QuestionType type,
      String correctAnswer, Category category) {
    this.title = title;
    this.description = description;
    this.difficulty = difficulty;
    this.type = type;
    this.correctAnswer = correctAnswer;
    this.category = category;
  }

}
