package com.pranav.ticketing.repo;
import com.pranav.ticketing.domain.Ticket;
import com.pranav.ticketing.domain.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface TicketRepo extends JpaRepository<Ticket, java.util.UUID> {
  Page<Ticket> findByOwner(User owner, Pageable pageable);
  Page<Ticket> findByAssignee(User assignee, Pageable pageable);
}
