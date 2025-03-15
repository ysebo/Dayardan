package kg.alatoo.midterm_project.exceptions;

import jakarta.validation.Valid;
import kg.alatoo.midterm_project.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MockController {

  @GetMapping("/nonexistent-endpoint")
  public void throwNotFoundException() {
    throw new NotFoundException("Resource not found");
  }

  @GetMapping("/illegal-argument")
  public void throwNotLegalArgumentException() {
    throw new NotLegalArgumentException("Illegal argument provided");
  }

  @PostMapping("/users")
  public void createUser(@Valid @RequestBody User user) {
  }
}
