package com.pranav.ticketing.web.dto;
import com.pranav.ticketing.domain.Ticket;
import java.time.Instant;
import java.util.UUID;
public record CreateTicketReq(String subject, String description, String priority) {}
public record AssignReq(UUID assigneeId) {}
public record StatusReq(String status) {}
public record CommentReq(String body) {}
public record TicketDto(UUID id, String subject, String description, String priority, String status,
                        UUID ownerId, UUID assigneeId, Instant createdAt, Instant updatedAt) {
  public static TicketDto from(Ticket t){
    return new TicketDto(t.getId(), t.getSubject(), t.getDescription(),
      t.getPriority().name(), t.getStatus().name(),
      t.getOwner()!=null? t.getOwner().getId(): null,
      t.getAssignee()!=null? t.getAssignee().getId(): null,
      t.getCreatedAt(), t.getUpdatedAt());
  }
}
