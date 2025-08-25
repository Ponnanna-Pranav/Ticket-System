package com.pranav.ticketing.web;
import com.pranav.ticketing.domain.*;
import com.pranav.ticketing.repo.UserRepo;
import com.pranav.ticketing.security.UserPrincipal;
import com.pranav.ticketing.svc.TicketService;
import com.pranav.ticketing.web.dto.*;
import org.springframework.data.domain.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/tickets")
public class TicketController {
  private final TicketService svc; private final UserRepo users;
  public TicketController(TicketService s, UserRepo u){svc=s; users=u;}
  @PostMapping
  public TicketDto create(@RequestBody CreateTicketReq req, @AuthenticationPrincipal UserPrincipal me){
    var p = Ticket.Priority.valueOf(req.priority()==null? "MEDIUM" : req.priority());
    return TicketDto.from(svc.create(req.subject(), req.description(), p, me.getId()));
  }
  @GetMapping
  public Page<TicketDto> list(@RequestParam(defaultValue="mine") String scope, @AuthenticationPrincipal UserPrincipal me,
                              @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size){
    var pageable = PageRequest.of(page, Math.min(size, 100), Sort.by(Sort.Direction.DESC,"createdAt"));
    return svc.list(scope, me, pageable).map(TicketDto::from);
  }
  @GetMapping("/{id}")
  public TicketDto get(@PathVariable UUID id){ return TicketDto.from(svc.get(id)); }
  @PatchMapping("/{id}/assignee")
  public TicketDto assign(@PathVariable UUID id, @RequestBody AssignReq req, @AuthenticationPrincipal UserPrincipal me){
    return TicketDto.from(svc.assign(id, req.assigneeId(), me));
  }
  @PatchMapping("/{id}/status")
  public TicketDto status(@PathVariable UUID id, @RequestBody StatusReq req, @AuthenticationPrincipal UserPrincipal me){
    return TicketDto.from(svc.setStatus(id, Ticket.Status.valueOf(req.status()), me));
  }
  @PostMapping("/{id}/comments")
  public Map<String,Object> comment(@PathVariable UUID id, @RequestBody CommentReq req, @AuthenticationPrincipal UserPrincipal me){
    var c = svc.comment(id, req.body(), me);
    return Map.of("id", c.getId(), "createdAt", c.getCreatedAt());
  }
  @GetMapping("/{id}/comments")
  public List<Map<String,Object>> comments(@PathVariable UUID id){
    return svc.comments(id).stream().map(c -> Map.of(
      "id", c.getId(), "authorId", c.getAuthor().getId(),
      "body", c.getBody(), "createdAt", c.getCreatedAt()
    )).toList();
  }
  @PostMapping("/{id}/rating")
  public Map<String,Object> rate(@PathVariable UUID id, @RequestParam int score, @RequestParam(required=false) String feedback,
                                 @AuthenticationPrincipal UserPrincipal me){
    var r = svc.rate(id, score, feedback, me);
    return Map.of("id", r.getId(), "score", r.getScore(), "feedback", r.getFeedback());
  }
}
