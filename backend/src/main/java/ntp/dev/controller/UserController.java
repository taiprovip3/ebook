package ntp.dev.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ntp.dev.entity.User;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	@GetMapping("/whoiam")
	public ResponseEntity<?> getUserById(@AuthenticationPrincipal User userPrincipal) {
		return ResponseEntity.ok(userPrincipal);
	}
	
}
