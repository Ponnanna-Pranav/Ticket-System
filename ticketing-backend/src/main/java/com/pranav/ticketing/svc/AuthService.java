package com.pranav.ticketing.svc;
import com.pranav.ticketing.domain.Role;
import com.pranav.ticketing.domain.User;
import com.pranav.ticketing.repo.RoleRepo;
import com.pranav.ticketing.repo.UserRepo;
import com.pranav.ticketing.security.JwtService;
import com.pranav.ticketing.security.UserPrincipal;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class AuthService {
  private final UserRepo users; private final RoleRepo roles; private final PasswordEncoder pw; private final JwtService jwt;
  public AuthService(UserRepo u, RoleRepo r, PasswordEncoder p, JwtService j){users=u; roles=r; pw=p; jwt=j;}
  @Transactional
  public Map<String,Object> register(String email, String name, String password){
    if (users.findByEmail(email).isPresent()) throw new RuntimeException("Email already registered");
    User u = new User(); u.setEmail(email); u.setName(name); u.setPasswordHash(pw.encode(password));
    u.getRoles().add(roles.findByName("USER"));
    users.save(u);
    return tokens(u);
  }
  public Map<String,Object> login(String email, String password){
    User u = users.findByEmail(email).orElseThrow(()->new RuntimeException("Invalid credentials"));
    if (!u.isEnabled() || !pw.matches(password, u.getPasswordHash())) throw new RuntimeException("Invalid credentials");
    return tokens(u);
  }
  private Map<String,Object> tokens(User u){
    List<String> r = u.getRoles().stream().map(Role::getName).toList();
    String at = jwt.createToken(u.getId().toString(), Map.of("email", u.getEmail(), "roles", r), false);
    String rt = jwt.createToken(u.getId().toString(), Map.of("email", u.getEmail(), "roles", r, "typ","refresh"), true);
    return Map.of("accessToken", at, "refreshToken", rt, "user", Map.of("id",u.getId(), "email", u.getEmail(), "name", u.getName(), "roles", r));
  }
  public UserPrincipal principal(User u){
    return new UserPrincipal(u.getId(), u.getEmail(), u.getRoles().stream().map(Role::getName).collect(java.util.stream.Collectors.toSet()));
  }
}
