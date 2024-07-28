package phb.ebookstore.dev.service;

import java.util.List;

import phb.ebookstore.dev.model.OrderItemRating;

public interface OrderItemRatingService {
	public List<OrderItemRating> getRatingsOfBook(long bookId);
	
}
