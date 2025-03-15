package kg.alatoo.midterm_project.controller.api;

import java.util.List;
import kg.alatoo.midterm_project.controller.api.documentation.CategoryControllerDocumentation;
import kg.alatoo.midterm_project.payload.response.CategoryResponse;
import kg.alatoo.midterm_project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerDocumentation {

  private final CategoryService categoryService;

  public ResponseEntity<List<CategoryResponse>> getAllCategories() {
    return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
  }

  public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
    return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
  }

  public ResponseEntity<CategoryResponse> createCategory(@RequestParam("name") String name) {
    return new ResponseEntity<>(categoryService.createCategory(name), HttpStatus.CREATED);
  }

  public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestParam("name") String name) {
    return new ResponseEntity<>(categoryService.updateCategory(id, name), HttpStatus.OK);
  }

  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
