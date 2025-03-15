package kg.alatoo.midterm_project.controller.api.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import kg.alatoo.midterm_project.payload.response.RoleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RequestMapping("/api/roles")
@Tag(name = "Role Controller", description = "APIs for managing roles")
public interface RoleControllerDocumentation {

  @GetMapping("/get-all")
  @Operation(summary = "Get all roles", description = "Get all roles from the database")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all roles"),
      @ApiResponse(responseCode = "500", description = "Internal server error"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  ResponseEntity<List<RoleResponse>> getAllRoles();

  @GetMapping("/get/{id}")
  @Operation(summary = "Get role by ID", description = "Get a role by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the role"),
      @ApiResponse(responseCode = "404", description = "Role not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id);

  @PutMapping("/update/{id}")
  @Operation(summary = "Update role", description = "Update a role by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated the role"),
      @ApiResponse(responseCode = "404", description = "Role not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @RequestParam String name);

  @PostMapping("/create")
  @Operation(summary = "Create role", description = "Create a new role")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created the role"),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<RoleResponse> createRole(@RequestParam String name);

  @DeleteMapping("/delete/{id}")
  @Operation(summary = "Delete role", description = "Delete a role by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Successfully deleted the role"),
      @ApiResponse(responseCode = "404", description = "Role not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<Void> deleteRole(@PathVariable Long id);
}
