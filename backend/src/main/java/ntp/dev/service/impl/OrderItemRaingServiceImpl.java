package ntp.dev.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntp.dev.model.OrderItemRating;
import ntp.dev.repository.OrderItemRatingRepository;
import ntp.dev.service.OrderItemRatingService;

@Service
public class OrderItemRaingServiceImpl implements OrderItemRatingService {

	@Autowired
	private OrderItemRatingRepository orderItemRatingRepository;
	
	@Override
	public List<OrderItemRating> getRatingsOfBook(long bookId) {
		return orderItemRatingRepository.getRatingsOfBook(bookId);
	}

}
