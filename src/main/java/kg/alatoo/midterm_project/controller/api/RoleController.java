package kg.alatoo.midterm_project.controller.api;

import java.util.List;
import kg.alatoo.midterm_project.controller.api.documentation.RoleControllerDocumentation;
import kg.alatoo.midterm_project.payload.response.RoleResponse;
import kg.alatoo.midterm_project.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class RoleController implements RoleControllerDocumentation {

  private final RoleService roleService;

  public ResponseEntity<List<RoleResponse>> getAllRoles() {
    return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
  }

  public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
    return new ResponseEntity<>(roleService.getRoleById(id), HttpStatus.OK);
  }

  public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @RequestParam String name) {
    return new ResponseEntity<>(roleService.updateRole(id, name), HttpStatus.OK);
  }

  public ResponseEntity<RoleResponse> createRole(@RequestParam String name) {
    return new ResponseEntity<>(roleService.createRole(name), HttpStatus.CREATED);
  }

  public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
    roleService.deleteRole(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
