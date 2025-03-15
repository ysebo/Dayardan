package kg.alatoo.midterm_project.service;

import java.util.List;
import kg.alatoo.midterm_project.payload.response.CategoryResponse;

public interface CategoryService {

  List<CategoryResponse> getAllCategories();

  CategoryResponse getCategoryById(Long id);

  CategoryResponse updateCategory(Long id, String name);

  void deleteCategory(Long id);
}
