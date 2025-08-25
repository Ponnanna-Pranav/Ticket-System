package com.pranav.ticketing.repo;
import com.pranav.ticketing.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UserRepo extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);
}
