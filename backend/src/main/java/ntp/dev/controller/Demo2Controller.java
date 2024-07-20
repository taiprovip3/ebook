package ntp.dev.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ntp.dev.data.UserV0;
import ntp.dev.entity.User;
import ntp.dev.security.auth.AuthenticationService;
import ntp.dev.service.UserService;
@RestController
@RequestMapping("/api/v1/management/")
@RequiredArgsConstructor
public class Demo2Controller {
	private final AuthenticationService service;
	private final UserService userService;
	 @GetMapping("/hello")
	  public ResponseEntity<String> sayHello() {
	    return ResponseEntity.ok("Hello from secured endpoint");
	  }
	 
	 @GetMapping("/getUserLogin")
		public User getUserLogin() {
			return service.getCurrentAuthenticatedIOKIUser();
		}
	 
	 @GetMapping("/getUserByEmail")
		public UserV0 getUserByEmail(@RequestParam String email) {
			return userService.getByEmail(email);
		}
}
