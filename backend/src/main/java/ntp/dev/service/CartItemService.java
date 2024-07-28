package ntp.dev.service;

import java.util.List;

import ntp.dev.dto.CustomCartItemUserResponse;
import ntp.dev.entity.User;
import ntp.dev.model.CartItem;
import ntp.dev.security.dto.AddToCartDTO;

public interface CartItemService {
	public List<CustomCartItemUserResponse> getUserCartItems(long userId);
	public CartItem findById(long cartItemId);
	public CartItem saveCartItem(CartItem cartItem);
	public void deleteById(long cartItemId);
	public boolean existsById(long id);
	public void deleteAllById(List<Long> cartItemIds);
	public String addToCart(AddToCartDTO addToCartDTO, User user);
}
