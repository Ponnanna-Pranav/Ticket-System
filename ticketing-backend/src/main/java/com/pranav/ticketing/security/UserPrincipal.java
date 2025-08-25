package com.pranav.ticketing.security;
import lombok.*;
import java.util.Set;
import java.util.UUID;
@Getter @AllArgsConstructor
public class UserPrincipal {
  private UUID id;
  private String email;
  private Set<String> roles;
  public boolean hasRole(String r) { return roles.stream().anyMatch(x -> x.equalsIgnoreCase(r)); }
}
