package phb.ebookstore.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.enumric.OrderStatus;
import phb.ebookstore.dev.security.config.JwtService;
import phb.ebookstore.dev.security.dto.OrderRequestDTO;
import phb.ebookstore.dev.service.OrderService;
import phb.ebookstore.dev.service.UserService;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
    private JwtService jwtUtil;
	
	@GetMapping("/list") // User dùng
	public ResponseEntity<?> list(@RequestHeader("Authorization") String token) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized role access resource");
		}
		
		return ResponseEntity.ok(orderService.findAll());
	}
	@GetMapping("/list/{orderStatus}")
	public ResponseEntity<?> list(@RequestHeader("Authorization") String token, @PathVariable OrderStatus orderStatus) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized role access resource");
		}
		
		return ResponseEntity.ok(orderService.findAllByOrderStatus(orderStatus));
	}
	@DeleteMapping("/cancelOrder/{orderId}")
	public ResponseEntity<?> cancelOrder(@RequestHeader("Authorization") String token, @PathVariable long orderId, @Nullable @RequestParam String reason) {
		System.out.println("---------------------reasonreasonreason------------------" + reason);
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized role access resource");
		}
		
		return ResponseEntity.ok().body(orderService.cancelOrder(orderId, reason));
	}
	
	
	@GetMapping("/getUserOrders") // User dùng
	public ResponseEntity<?> getUserOrders(@RequestHeader("Authorization") String token) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized role access resource");
		}
		
		return ResponseEntity.ok(orderService.findAllByUser(user));
	}
	
	@GetMapping("/getUserOrders/{orderStatus}")
	public ResponseEntity<?> getUserOrders(@RequestHeader("Authorization") String token, @PathVariable OrderStatus orderStatus) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized role access resource");
		}
		
		return ResponseEntity.ok(orderService.findAllByUserAndOrderStatus(orderStatus, user));
	}
	
	@PostMapping("/createOrder")
	public ResponseEntity<?> createOrder(@RequestHeader("Authorization") String token, @Validated @RequestBody OrderRequestDTO orderRequestDTO) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized role access resource");
		}
		
		System.out.println("orderRequestDTO = "+ orderRequestDTO);
		return ResponseEntity.ok(orderService.createOrder(orderRequestDTO, user));
	}
	
	@PatchMapping("/confirmOrder/{orderId}")
	public ResponseEntity<?> confirmOrder(@RequestHeader("Authorization") String token, @PathVariable long orderId) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized role access resource");
		}
		
		try {
			orderService.confirmOrder(orderId);
			return ResponseEntity.ok("Confirm Order Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping("/handOverOrder/{orderId}")
	public ResponseEntity<?> handOverOrder(@RequestHeader("Authorization") String token, @PathVariable long orderId) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized role access resource");
		}
		
		try {
			orderService.handOverOrder(orderId);
			return ResponseEntity.ok("Hand over Order Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
