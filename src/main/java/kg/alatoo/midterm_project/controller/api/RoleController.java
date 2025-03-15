package kg.alatoo.midterm_project.controller.api;

import java.util.List;
import kg.alatoo.midterm_project.payload.response.RoleResponse;
import kg.alatoo.midterm_project.service.RoleService;
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
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;


  @GetMapping("/get-all")
  public ResponseEntity<List<RoleResponse>> getAllRoles() {
    return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
    return new ResponseEntity<>(roleService.getRoleById(id), HttpStatus.OK);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, String name) {
    return new ResponseEntity<>(roleService.updateRole(id, name), HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<RoleResponse> createRole(@RequestParam String name) {
    return new ResponseEntity<>(roleService.createRole(name), HttpStatus.CREATED);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
    roleService.deleteRole(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
