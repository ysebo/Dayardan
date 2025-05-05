package kg.alatoo.midterm_project.mapper;

import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.payload.request.UserRequest;
import kg.alatoo.midterm_project.payload.response.UserResponse;
import kg.alatoo.midterm_project.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  private final RoleRepository roleRepository;

  public UserMapper(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public User toEntity(UserRequest userRequest) {
    User user = new User();
    user.setUsername(userRequest.username());
    user.setEmail(userRequest.email());
    user.setUserRole(roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Default role not found")));
    return user;
  }
  public UserResponse toModel(User user) {
    return new UserResponse(
        user.getId(),
        user.getUsername(),
        user.getEmail()
    );
  }

}
