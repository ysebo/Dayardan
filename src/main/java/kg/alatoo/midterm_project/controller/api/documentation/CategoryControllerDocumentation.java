package kg.alatoo.midterm_project.controller.api.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.alatoo.midterm_project.payload.response.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
@Tag(name = "Category Controller", description = "APIs for managing categories")
public interface CategoryControllerDocumentation {

  @GetMapping("/get-all")
  @Operation(summary = "Get all categories", description = "Get all categories from the database")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories"),
      @ApiResponse(responseCode = "500", description = "Internal server error"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  ResponseEntity<List<CategoryResponse>> getAllCategories();

  @GetMapping("/get/{id}")
  @Operation(summary = "Get category by ID", description = "Get a category by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the category"),
      @ApiResponse(responseCode = "404", description = "Category not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id);

  @PostMapping("/create")
  @Operation(summary = "Create category", description = "Create a new category")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created the category"),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<CategoryResponse> createCategory(@RequestParam("name") String name);

  @PutMapping("/update/{id}")
  @Operation(summary = "Update category", description = "Update a category by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated the category"),
      @ApiResponse(responseCode = "404", description = "Category not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestParam("name") String name);

  @DeleteMapping("/delete/{id}")
  @Operation(summary = "Delete category", description = "Delete a category by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Successfully deleted the category"),
      @ApiResponse(responseCode = "404", description = "Category not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<Void> deleteCategory(@PathVariable Long id);
}
