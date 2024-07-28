package phb.ebookstore.dev.service;

import java.util.List;

import phb.ebookstore.dev.dto.CustomCartItemUserResponse;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.model.CartItem;
import phb.ebookstore.dev.security.dto.AddToCartDTO;

public interface CartItemService {
	public List<CustomCartItemUserResponse> getUserCartItems(long userId);
	public CartItem findById(long cartItemId);
	public CartItem saveCartItem(CartItem cartItem);
	public void deleteById(long cartItemId);
	public boolean existsById(long id);
	public void deleteAllById(List<Long> cartItemIds);
	public String addToCart(AddToCartDTO addToCartDTO, User user);
}
