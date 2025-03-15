package kg.alatoo.midterm_project.service.impl;

import java.util.List;
import kg.alatoo.midterm_project.entity.Category;
import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.payload.response.CategoryResponse;
import kg.alatoo.midterm_project.repository.CategoryRepository;
import kg.alatoo.midterm_project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  public List<CategoryResponse> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(category -> new CategoryResponse(category.getId(), category.getName()))
        .toList();
  }

  public CategoryResponse getCategoryById(Long id) {
    return categoryRepository.findById(id)
        .map(category -> new CategoryResponse(category.getId(), category.getName()))
        .orElseThrow(() -> new NotFoundException("Category not found"));
  }

  public CategoryResponse createCategory(String name) {
    Category category = new Category();
    category.setName(name);
    Category savedCategory = categoryRepository.save(category);
    return new CategoryResponse(savedCategory.getId(), savedCategory.getName());
  }

  public CategoryResponse updateCategory(Long id, String name) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Category not found"));
    category.setName(name);
    Category savedCategory = categoryRepository.save(category);
    return new CategoryResponse(savedCategory.getId(), savedCategory.getName());
  }

  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }
}
