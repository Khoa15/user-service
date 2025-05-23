package com.example.homeserver.Controller;


import com.example.homeserver.DTO.LoginDTO;
import com.example.homeserver.DTO.ResLoginDTO;
import com.example.homeserver.Util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    // Dependency injection
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    public AuthenticationController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO login) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                login.getUsername(),
                login.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = this.securityUtil.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResLoginDTO loginDTO = new ResLoginDTO(login.getUsername(), accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(loginDTO);
    }

}
