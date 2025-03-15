package kg.alatoo.midterm_project.controller.api;

import java.util.List;
import kg.alatoo.midterm_project.payload.response.CategoryResponse;
import kg.alatoo.midterm_project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;


  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getAllCategories() {
    return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
    return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> createCategory(@RequestParam("name") String name) {
    return new ResponseEntity<>(categoryService.createCategory(name), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,
      @RequestParam("name") String name) {
    return new ResponseEntity<>(categoryService.updateCategory(id, name), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
