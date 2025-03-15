package kg.alatoo.midterm_project.service.impl;

import java.util.List;
import kg.alatoo.midterm_project.entity.UserRole;
import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.payload.response.RoleResponse;
import kg.alatoo.midterm_project.repository.RoleRepository;
import kg.alatoo.midterm_project.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  public List<RoleResponse> getAllRoles() {
    return roleRepository.findAll().stream().map(role -> new RoleResponse(role.getId(), role.getName())).toList();
  }

  public RoleResponse getRoleById(Long id) {
    UserRole role = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
    return new RoleResponse(role.getId(), role.getName());
  }

  public RoleResponse createRole(String name) {
    UserRole role = new UserRole();
    role.setName(name);
    UserRole savedRole = roleRepository.save(role);
    return new RoleResponse(savedRole.getId(), savedRole.getName());
  }

  public RoleResponse updateRole(Long id, String name) {
    UserRole role = roleRepository.findById(id).
        orElseThrow(() -> new NotFoundException("Role not found"));
    role.setName(name);
    UserRole savedRole = roleRepository.save(role);
    return new RoleResponse(savedRole.getId(), savedRole.getName());
  }

  public void deleteRole(Long id) {
    roleRepository.deleteById(id);
  }
}
