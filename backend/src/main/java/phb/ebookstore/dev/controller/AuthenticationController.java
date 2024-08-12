package phb.ebookstore.dev.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import phb.ebookstore.dev.entity.Role;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.security.auth.AuthenticationService;
import phb.ebookstore.dev.security.config.JwtService;
import phb.ebookstore.dev.security.dto.AuthenticationRequest;
import phb.ebookstore.dev.security.dto.AuthenticationResponse;
import phb.ebookstore.dev.security.dto.ChangePasswordRequest;
import phb.ebookstore.dev.security.dto.RegisterRequest;
import phb.ebookstore.dev.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService service;
	@Autowired
    private JwtService jwtUtil;
	@Autowired
	private UserService userService;

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
	
	@PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestBody ChangePasswordRequest request) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(!user.getRole().equals(Role.USER)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized USER role access resource");
		}
		System.out.println("request=" + request);
        boolean isPasswordChanged = userService.changePassword(username, request.getOldPassword(), request.getNewPassword());
        
        if (isPasswordChanged) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to change password. Old password might be incorrect.");
        }
    }
}