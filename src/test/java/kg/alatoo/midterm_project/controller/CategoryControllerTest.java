package kg.alatoo.midterm_project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import kg.alatoo.midterm_project.controller.api.CategoryController;
import kg.alatoo.midterm_project.payload.response.CategoryResponse;
import kg.alatoo.midterm_project.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CategoryService categoryService;

  private CategoryResponse category1;
  private CategoryResponse category2;

  @BeforeEach
  void setUp() {
    category1 = new CategoryResponse(1L, "Java");
    category2 = new CategoryResponse(2L, "Spring Boot");
  }

  @Test
  void getAllCategories_ShouldReturnListOfCategories() throws Exception {
    List<CategoryResponse> categories = Arrays.asList(category1, category2);
    when(categoryService.getAllCategories()).thenReturn(categories);

    mockMvc.perform(get("/api/categories/get-all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Java"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("Spring Boot"));
  }

  @Test
  void getCategoryById_ShouldReturnCategory() throws Exception {
    when(categoryService.getCategoryById(1L)).thenReturn(category1);

    mockMvc.perform(get("/api/categories/get/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Java"));
  }

  @Test
  void createCategory_ShouldReturnCreatedCategory() throws Exception {
    when(categoryService.createCategory(any())).thenReturn(category1);

    mockMvc.perform(post("/api/categories/create")
            .param("name", "Java")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Java"));
  }

  @Test
  void updateCategory_ShouldReturnUpdatedCategory() throws Exception {
    when(categoryService.updateCategory(anyLong(), any())).thenReturn(category2);

    mockMvc.perform(put("/api/categories/update/1")
            .param("name", "Spring Boot")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.name").value("Spring Boot"));
  }

  @Test
  void deleteCategory_ShouldReturnNoContent() throws Exception {
    doNothing().when(categoryService).deleteCategory(anyLong());

    mockMvc.perform(delete("/api/categories/delete/1"))
        .andExpect(status().isNoContent());
  }
}