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
import kg.alatoo.midterm_project.controller.api.RoleController;
import kg.alatoo.midterm_project.payload.response.RoleResponse;
import kg.alatoo.midterm_project.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
@WebMvcTest(RoleController.class)
class RoleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RoleService roleService;

  private RoleResponse role1;
  private RoleResponse role2;

  @BeforeEach
  void setUp() {
    role1 = new RoleResponse(1L, "ADMIN");
    role2 = new RoleResponse(2L, "USER");
  }

  @Test
  void getAllRoles_ShouldReturnListOfRoles() throws Exception {
    List<RoleResponse> roles = Arrays.asList(role1, role2);
    when(roleService.getAllRoles()).thenReturn(roles);

    mockMvc.perform(get("/api/roles/get-all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("ADMIN"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("USER"));
  }

  @Test
  void getRoleById_ShouldReturnRole() throws Exception {
    when(roleService.getRoleById(1L)).thenReturn(role1);

    mockMvc.perform(get("/api/roles/get/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("ADMIN"));
  }

  @Test
  void createRole_ShouldReturnCreatedRole() throws Exception {
    when(roleService.createRole(any())).thenReturn(role1);

    mockMvc.perform(post("/api/roles/create")
            .param("name", "ADMIN")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("ADMIN"));
  }

  @Test
  void updateRole_ShouldReturnUpdatedRole() throws Exception {
    when(roleService.updateRole(anyLong(), any())).thenReturn(role2);

    mockMvc.perform(put("/api/roles/update/1")
            .param("name", "USER")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.name").value("USER"));
  }

  @Test
  void deleteRole_ShouldReturnNoContent() throws Exception {
    doNothing().when(roleService).deleteRole(anyLong());

    mockMvc.perform(delete("/api/roles/delete/1"))
        .andExpect(status().isNoContent());
  }
}