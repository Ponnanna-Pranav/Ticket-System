package com.pranav.ticketing.repo;
import com.pranav.ticketing.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoleRepo extends JpaRepository<Role, Integer> {
  Role findByName(String name);
}
