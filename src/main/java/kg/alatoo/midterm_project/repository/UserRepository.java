package kg.alatoo.midterm_project.repository;

import kg.alatoo.midterm_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
