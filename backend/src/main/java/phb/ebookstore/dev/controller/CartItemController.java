package phb.ebookstore.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import phb.ebookstore.dev.entity.Role;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.model.Book;
import phb.ebookstore.dev.model.CartItem;
import phb.ebookstore.dev.security.config.JwtService;
import phb.ebookstore.dev.security.dto.AddToCartDTO;
import phb.ebookstore.dev.service.CartItemService;
import phb.ebookstore.dev.service.UserService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartItemController {
	
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private UserService userService;
	@Autowired
    private JwtService jwtUtil;

	@GetMapping("/getUserCartItems")
	public ResponseEntity<?> getUserCartItems(@RequestHeader("Authorization") String token) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		return ResponseEntity.ok(cartItemService.getUserCartItems(user.getId()));
	}
	
	@PutMapping("/cartItem/addToCart")
	public ResponseEntity<?> addToCart(@RequestHeader("Authorization") String token, @RequestBody AddToCartDTO addToCartDTO) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		return ResponseEntity.ok(cartItemService.addToCart(addToCartDTO, user));
	}
	
	@PatchMapping("/cartItem/{cartItemId}")
    public ResponseEntity<?> updateCartItemQuantity(@RequestHeader("Authorization") String token, @PathVariable long cartItemId, @RequestParam int quantity) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(!user.getRole().equals(Role.USER)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized USER role access resource");
		}
		
        // Validate quantity
        if (quantity <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity must be greater than zero.");
        }

        CartItem cartItem = cartItemService.findById(cartItemId);
        if (cartItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found.");
        }

        Book book = cartItem.getBook();

        // Validate stock
        if (quantity > book.getStockQuantity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity exceeds stock available.");
        }

        cartItem.setQuantity(quantity);
        cartItemService.saveCartItem(cartItem);

        return ResponseEntity.ok("Cart item updated successfully.");
    }
	
	@DeleteMapping("/cartItem/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@RequestHeader("Authorization") String token, @PathVariable Long cartItemId) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(!user.getRole().equals(Role.USER)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized USER role access resource");
		}
		
		CartItem cartItem = cartItemService.findById(cartItemId);
        if (cartItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found.");
        }

        cartItemService.deleteById(cartItemId);
        return ResponseEntity.ok("Cart item deleted successfully.");
    }
	
	@DeleteMapping("/cartItem/deleteMany")
    public ResponseEntity<?> deleteCartItems(@RequestHeader("Authorization") String token, @RequestBody List<Long> cartItemIds) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(!user.getRole().equals(Role.USER)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized USER role access resource");
		}
		
        for (Long id : cartItemIds) {
            if (!cartItemService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item with ID " + id + " not found.");
            }
        }

        cartItemService.deleteAllById(cartItemIds);
        return ResponseEntity.ok("Cart items deleted successfully.");
    }
}
