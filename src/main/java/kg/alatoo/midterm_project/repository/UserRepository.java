package kg.alatoo.midterm_project.repository;

import java.util.Optional;
import kg.alatoo.midterm_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
  Optional<User> findByUsername(String username);
}
