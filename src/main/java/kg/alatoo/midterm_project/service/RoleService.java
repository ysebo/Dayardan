package kg.alatoo.midterm_project.service;

import java.util.List;
import kg.alatoo.midterm_project.payload.response.RoleResponse;

public interface RoleService {

  List<RoleResponse> getAllRoles();

  RoleResponse getRoleById(Long id);

  RoleResponse updateRole(Long id, String name);

  RoleResponse createRole(String name);

  void deleteRole(Long id);
}
