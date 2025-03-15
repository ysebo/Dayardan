package kg.alatoo.midterm_project.repository;

import kg.alatoo.midterm_project.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
}
