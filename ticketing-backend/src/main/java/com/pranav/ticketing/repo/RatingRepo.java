package com.pranav.ticketing.repo;
import com.pranav.ticketing.domain.Rating;
import com.pranav.ticketing.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface RatingRepo extends JpaRepository<Rating, java.util.UUID> {
  Optional<Rating> findByTicket(Ticket ticket);
}
