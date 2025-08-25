package com.pranav.ticketing.svc;
import com.pranav.ticketing.domain.*;
import com.pranav.ticketing.repo.*;
import com.pranav.ticketing.security.UserPrincipal;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;
@Service
public class TicketService {
  private final TicketRepo tickets; private final UserRepo users; private final CommentRepo comments; private final RatingRepo ratings;
  public TicketService(TicketRepo t, UserRepo u, CommentRepo c, RatingRepo r){tickets=t; users=u; comments=c; ratings=r;}
  public Ticket get(UUID id){ return tickets.findById(id).orElseThrow(); }
  @Transactional
  public Ticket create(String subject, String description, Ticket.Priority priority, UUID ownerId){
    Ticket t = new Ticket();
    t.setSubject(subject); t.setDescription(description); t.setPriority(priority);
    t.setOwner(users.findById(ownerId).orElseThrow());
    return tickets.save(t);
  }
  public Page<Ticket> list(String scope, UserPrincipal me, Pageable pageable){
    if ("all".equals(scope)) return new PageImpl<>(tickets.findAll(pageable).getContent(), pageable, tickets.count());
    var user = users.findById(me.getId()).orElseThrow();
    if (me.hasRole("ADMIN")) return tickets.findAll(pageable);
    if (me.hasRole("AGENT")) {
      var pageAssigned = tickets.findByAssignee(user, pageable);
      return pageAssigned;
    }
    return tickets.findByOwner(user, pageable);
  }
  @Transactional
  public Ticket assign(UUID id, UUID assigneeId, UserPrincipal actor){
    Ticket t = get(id);
    if (!(actor.hasRole("ADMIN") || actor.hasRole("AGENT"))) throw new RuntimeException("Not allowed");
    var assignee = users.findById(assigneeId).orElseThrow();
    t.setAssignee(assignee);
    t.setUpdatedAt(Instant.now());
    return t;
  }
  @Transactional
  public Ticket setStatus(UUID id, Ticket.Status s, UserPrincipal actor){
    Ticket t = get(id);
    if (actor.hasRole("ADMIN") || (actor.hasRole("AGENT") && t.getAssignee()!=null && t.getAssignee().getId().equals(actor.getId()))) {
      t.setStatus(s); t.setUpdatedAt(Instant.now());
      if (s==Ticket.Status.RESOLVED) t.setResolvedAt(Instant.now());
      if (s==Ticket.Status.CLOSED) t.setClosedAt(Instant.now());
      return t;
    }
    throw new RuntimeException("Not allowed");
  }
  @Transactional
  public Comment comment(UUID ticketId, String body, UserPrincipal actor){
    Ticket t = get(ticketId);
    // owner, assignee, admin can comment
    if (!(actor.hasRole("ADMIN") || (t.getOwner().getId().equals(actor.getId())) ||
          (t.getAssignee()!=null && t.getAssignee().getId().equals(actor.getId()))))
      throw new RuntimeException("Not allowed");
    Comment c = new Comment(); c.setTicket(t);
    c.setAuthor(users.findById(actor.getId()).orElseThrow()); c.setBody(body);
    return comments.save(c);
  }
  public List<Comment> comments(UUID ticketId){
    return comments.findByTicketOrderByCreatedAtAsc(get(ticketId));
  }
  @Transactional
  public Rating rate(UUID ticketId, int score, String feedback, UserPrincipal actor){
    Ticket t = get(ticketId);
    if (!(t.getOwner().getId().equals(actor.getId()))) throw new RuntimeException("Only owner can rate");
    if (!(t.getStatus()==Ticket.Status.RESOLVED || t.getStatus()==Ticket.Status.CLOSED)) throw new RuntimeException("Ticket not resolved/closed");
    if (ratings.findByTicket(t).isPresent()) throw new RuntimeException("Already rated");
    Rating r = new Rating(); r.setTicket(t);
    r.setRater(users.findById(actor.getId()).orElseThrow()); r.setScore(score); r.setFeedback(feedback);
    return ratings.save(r);
  }
}
