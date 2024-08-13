package phb.ebookstore.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.security.config.JwtService;
import phb.ebookstore.dev.service.OrderItemRatingService;
import phb.ebookstore.dev.service.UserService;

@RestController
@RequestMapping("/api/v1/order-item")
@RequiredArgsConstructor
public class OrderItemController {
	
	@Autowired
	private UserService userService;
	@Autowired
    private JwtService jwtUtil;
	@Autowired
	private OrderItemRatingService orderItemRatingService;
	
	@GetMapping("/rating/{orderItemId}")
    public ResponseEntity<?> getOrderItemRatingById(@RequestHeader("Authorization") String token, @PathVariable long orderItemId) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized any role access resource");
		}
        return ResponseEntity.ok(orderItemRatingService.getRatingOfBook(orderItemId));
    }
}
