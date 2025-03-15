package kg.alatoo.midterm_project.repository;

import kg.alatoo.midterm_project.entity.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CategoryRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CategoryRepository categoryRepository;

  @AfterEach
  void tearDown() {
    entityManager.clear();
  }

  @Test
  void findById_ShouldReturnCategory() {
    Category category = new Category();
    category.setName("Programming");
    entityManager.persist(category);

    Optional<Category> foundCategory = categoryRepository.findById(category.getId());

    assertTrue(foundCategory.isPresent());
    assertEquals(category.getName(), foundCategory.get().getName());
  }
}