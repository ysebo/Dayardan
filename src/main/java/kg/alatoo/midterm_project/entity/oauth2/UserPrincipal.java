package kg.alatoo.midterm_project.entity.oauth2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kg.alatoo.midterm_project.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserPrincipal implements OAuth2User, UserDetails {
  private Long id;
  private String email;
  private String password;
  private String name;
  private String role;
  private Map<String, Object> attributes;

  public static UserPrincipal create(User user, Map<String, Object> attributes) {
    UserPrincipal principal = new UserPrincipal();
    principal.id = user.getId();
    principal.email = user.getEmail();
    principal.password = user.getPassword();
    principal.name = user.getUsername();
    principal.role = user.getUserRole().getName();
    principal.attributes = attributes;
    return principal;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role));
  }

  @Override
  public String getName() {
    return name;
  }
}
