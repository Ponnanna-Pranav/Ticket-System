package com.pranav.ticketing.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="comments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Comment {
  @Id @GeneratedValue private UUID id;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="ticket_id") private Ticket ticket;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="author_id") private User author;
  @Column(columnDefinition="text") private String body;
  @Column(name="created_at") private Instant createdAt = Instant.now();
}
