package com.pranav.ticketing.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.*;
@Entity @Table(name="users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
  @Id @GeneratedValue private UUID id;
  @Column(unique = true, nullable=false) private String email;
  @Column(name="password_hash", nullable=false) private String passwordHash;
  @Column(nullable=false) private String name;
  private boolean enabled = true;
  @Column(name="created_at") private Instant createdAt = Instant.now();
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="user_roles",
     joinColumns=@JoinColumn(name="user_id"),
     inverseJoinColumns=@JoinColumn(name="role_id"))
  private Set<Role> roles = new HashSet<>();
  public boolean hasRole(String r) {
    return roles.stream().anyMatch(x -> x.getName().equalsIgnoreCase(r));
  }
}
