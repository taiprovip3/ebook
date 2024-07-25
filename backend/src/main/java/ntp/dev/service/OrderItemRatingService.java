package ntp.dev.service;

import java.util.List;

import ntp.dev.model.OrderItemRating;

public interface OrderItemRatingService {
	public List<OrderItemRating> getRatingsOfBook(long bookId);
	
}
