package com.pranav.ticketing.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="ratings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Rating {
  @Id @GeneratedValue private UUID id;
  @OneToOne(fetch=FetchType.LAZY) @JoinColumn(name="ticket_id") private Ticket ticket;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="rater_id") private User rater;
  private int score;
  @Column(columnDefinition="text") private String feedback;
  @Column(name="created_at") private Instant createdAt = Instant.now();
}
