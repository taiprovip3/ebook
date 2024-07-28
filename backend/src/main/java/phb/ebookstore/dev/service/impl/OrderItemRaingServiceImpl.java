package phb.ebookstore.dev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phb.ebookstore.dev.model.OrderItemRating;
import phb.ebookstore.dev.repository.OrderItemRatingRepository;
import phb.ebookstore.dev.service.OrderItemRatingService;

@Service
public class OrderItemRaingServiceImpl implements OrderItemRatingService {

	@Autowired
	private OrderItemRatingRepository orderItemRatingRepository;
	
	@Override
	public List<OrderItemRating> getRatingsOfBook(long bookId) {
		return orderItemRatingRepository.getRatingsOfBook(bookId);
	}

}
