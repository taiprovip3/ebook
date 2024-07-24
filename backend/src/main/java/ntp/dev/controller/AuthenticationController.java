package ntp.dev.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ntp.dev.security.auth.AuthenticationService;
import ntp.dev.security.dto.AuthenticationRequest;
import ntp.dev.security.dto.AuthenticationResponse;
import ntp.dev.security.dto.RegisterRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService service;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		try {
			return ResponseEntity.ok(service.register(request));
		} catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest loginDTO) {
		AuthenticationResponse authenticate = service.authenticate(loginDTO);
		String refreshToken = authenticate.getRefreshToken();
		ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(7 * 24 * 60 * 60)
				.sameSite("Strict")
				.build();
		return ResponseEntity.ok()
		        .header(HttpHeaders.SET_COOKIE, cookie.toString())
		        .body(authenticate);
	}

	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		service.refreshToken(request, response);
	}
	
	@PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        try {
        	String token = authorizationHeader.substring(7);
        	System.out.println("Token to logout=" + token);
            service.logout(token);
            return ResponseEntity.ok(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }
}