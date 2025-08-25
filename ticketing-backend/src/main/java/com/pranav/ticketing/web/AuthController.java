package com.pranav.ticketing.web;
import com.pranav.ticketing.svc.AuthService;
import com.pranav.ticketing.domain.*;
import com.pranav.ticketing.repo.*;
import com.pranav.ticketing.web.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthService auth;
  public AuthController(AuthService a){this.auth=a;}
  @PostMapping("/register")
  public Map<String,Object> register(@RequestBody RegisterReq req){
    return auth.register(req.email(), req.name(), req.password());
  }
  @PostMapping("/login")
  public Map<String,Object> login(@RequestBody LoginReq req){
    return auth.login(req.email(), req.password());
  }
}
