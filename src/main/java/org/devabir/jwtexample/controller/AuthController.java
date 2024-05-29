package org.devabir.jwtexample.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.devabir.jwtexample.model.TokenResponse;
import org.devabir.jwtexample.model.UserRequestDto;
import org.devabir.jwtexample.model.UserResponseDto;
import org.devabir.jwtexample.service.AuthService;
import org.devabir.jwtexample.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.authService.register(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto user = authService.login(userRequestDto);
        final String accessToken = jwtService.buildToken(user.getEmail());
        return ResponseEntity.ok(new TokenResponse(accessToken, user));
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String email = userDetails.getUsername();
        return ResponseEntity.ok(this.authService.profile(email));
    }

}
