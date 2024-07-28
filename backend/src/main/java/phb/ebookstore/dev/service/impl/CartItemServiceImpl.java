package phb.ebookstore.dev.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import phb.ebookstore.dev.dto.CustomCartItemUserResponse;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.model.Book;
import phb.ebookstore.dev.model.Cart;
import phb.ebookstore.dev.model.CartItem;
import phb.ebookstore.dev.repository.BookRepository;
import phb.ebookstore.dev.repository.CartItemRepository;
import phb.ebookstore.dev.repository.CartRepository;
import phb.ebookstore.dev.security.dto.AddToCartDTO;
import phb.ebookstore.dev.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<CustomCartItemUserResponse> getUserCartItems(long userId) {
		List<CustomCartItemUserResponse> userCartItemsDTO = new ArrayList<CustomCartItemUserResponse>();
		List<CartItem> userCartItems = cartItemRepository.getUserCartItems(userId);
		for (CartItem cartItem : userCartItems) {
			CustomCartItemUserResponse dto = CustomCartItemUserResponse
					.builder()
					.cartItem(cartItem)
					.book(cartItem.getBook())
					.build();
			userCartItemsDTO.add(dto);
		}
		return userCartItemsDTO;
	}

	@Override
	public CartItem findById(long cartItemId) {
		return cartItemRepository.findById(cartItemId).orElse(null);
	}

	@Override
	public CartItem saveCartItem(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	@Override
	public void deleteById(long cartItemId) {
		cartItemRepository.deleteById(cartItemId);
	}

	@Override
	public boolean existsById(long cartItemId) {
		return cartItemRepository.existsById(cartItemId);
	}

	@Override
	public void deleteAllById(List<Long> cartItemIds) {
		cartItemRepository.deleteAllById(cartItemIds);
	}

	@Override
	@Transactional
	public String addToCart(AddToCartDTO addToCartDTO, User user) {
		try {
			Cart cart = cartRepository.findByUser(user);
			Book book = bookRepository.findById(addToCartDTO.getBookId()).orElse(null);
			System.out.println("book======" + book);
			
			CartItem byCartAndBook = cartItemRepository.findFirstByCartAndBook(cart, book);
			if(byCartAndBook != null) {// Đã tồn tại sách trong giỏ hàng
				int quantity = addToCartDTO.getQuantity();
				int stockQuantity = book.getStockQuantity();
				int totalQuantityInCart = byCartAndBook.getQuantity() + quantity;
				if(byCartAndBook.getQuantity() + quantity > stockQuantity) {
					return "Vượt quá Stocks sản phẩm";
				}
				byCartAndBook.setQuantity(totalQuantityInCart);
				cartItemRepository.save(byCartAndBook);
				return "Đã cập nhật vào giỏ hàng";
			}
			CartItem cartItem = CartItem
					.builder()
					.cart(cart)
					.book(book)
					.quantity(addToCartDTO.getQuantity())
					.build();
			cartItemRepository.save(cartItem);
			return "Đã thêm vào giỏ hàng";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
