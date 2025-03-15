package kg.alatoo.midterm_project.repository;

import javax.management.relation.Role;
import kg.alatoo.midterm_project.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
  @Query("SELECT r FROM UserRole r WHERE r.name = :name")
  Role findByName(String name);

}
