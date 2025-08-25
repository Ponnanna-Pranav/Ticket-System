package com.pranav.ticketing.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="tickets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Ticket {
  public enum Status { OPEN, IN_PROGRESS, RESOLVED, CLOSED }
  public enum Priority { LOW, MEDIUM, HIGH, URGENT }
  @Id @GeneratedValue private UUID id;
  private String subject;
  @Column(columnDefinition="text") private String description;
  @Enumerated(EnumType.STRING) private Priority priority = Priority.MEDIUM;
  @Enumerated(EnumType.STRING) private Status status = Status.OPEN;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="owner_id") private User owner;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="assignee_id") private User assignee;
  @Column(name="created_at") private Instant createdAt = Instant.now();
  @Column(name="updated_at") private Instant updatedAt = Instant.now();
  @Column(name="resolved_at") private Instant resolvedAt;
  @Column(name="closed_at") private Instant closedAt;
}
