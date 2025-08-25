package com.pranav.ticketing.bootstrap;
import com.pranav.ticketing.domain.Role;
import com.pranav.ticketing.domain.User;
import com.pranav.ticketing.repo.RoleRepo;
import com.pranav.ticketing.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class DataLoader implements CommandLineRunner {
  private final UserRepo users; private final RoleRepo roles; private final PasswordEncoder pw;
  public DataLoader(UserRepo u, RoleRepo r, PasswordEncoder p){users=u; roles=r; pw=p;}
  @Override public void run(String... args) {
    Role admin = roles.findByName("ADMIN"); Role agent = roles.findByName("AGENT"); Role userR = roles.findByName("USER");
    users.findByEmail("admin@demo.com").ifPresentOrElse(u->{
      u.setPasswordHash(pw.encode("admin123"));
      u.setRoles(new java.util.HashSet<>(List.of(admin))); users.save(u);
    }, ()->{});
    users.findByEmail("agent@demo.com").ifPresentOrElse(u->{
      u.setPasswordHash(pw.encode("agent123"));
      u.setRoles(new java.util.HashSet<>(List.of(agent))); users.save(u);
    }, ()->{});
    users.findByEmail("user@demo.com").ifPresentOrElse(u->{
      u.setPasswordHash(pw.encode("user123"));
      u.setRoles(new java.util.HashSet<>(List.of(userR))); users.save(u);
    }, ()->{});
  }
}
