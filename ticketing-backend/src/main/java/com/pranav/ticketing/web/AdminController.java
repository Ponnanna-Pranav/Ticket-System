package com.pranav.ticketing.web;
import com.pranav.ticketing.domain.Role;
import com.pranav.ticketing.domain.User;
import com.pranav.ticketing.repo.RoleRepo;
import com.pranav.ticketing.repo.UserRepo;
import com.pranav.ticketing.security.UserPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/admin")
public class AdminController {
  private final UserRepo users; private final RoleRepo roles;
  public AdminController(UserRepo u, RoleRepo r){users=u; roles=r;}
  private void requireAdmin(UserPrincipal me){
    if (me==null || !me.hasRole("ADMIN")) throw new RuntimeException("Admin only");
  }
  @GetMapping("/users")
  public List<Map<String,Object>> listUsers(@RequestParam(required=false) String role){
    return users.findAll().stream().filter(u-> role==null || u.getRoles().stream().anyMatch(x->x.getName().equalsIgnoreCase(role)))
      .map(u->Map.of("id",u.getId(),"email",u.getEmail(),"name",u.getName(),
        "roles", u.getRoles().stream().map(Role::getName).toList()))
      .toList();
  }
  @PostMapping("/users/{id}/roles")
  public Map<String,Object> setRoles(@PathVariable UUID id, @RequestBody List<String> roleNames, @org.springframework.security.core.annotation.AuthenticationPrincipal UserPrincipal me){
    requireAdmin(me);
    User u = users.findById(id).orElseThrow();
    Set<Role> rs = new java.util.HashSet<>();
    for (String rn : roleNames) { var r = roles.findByName(rn); if (r!=null) rs.add(r); }
    u.setRoles(rs);
    users.save(u);
    return Map.of("ok", true);
  }
}
