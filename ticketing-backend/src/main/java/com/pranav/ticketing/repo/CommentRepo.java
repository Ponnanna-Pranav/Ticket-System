package com.pranav.ticketing.repo;
import com.pranav.ticketing.domain.Comment;
import com.pranav.ticketing.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface CommentRepo extends JpaRepository<Comment, java.util.UUID> {
  List<Comment> findByTicketOrderByCreatedAtAsc(Ticket ticket);
}
