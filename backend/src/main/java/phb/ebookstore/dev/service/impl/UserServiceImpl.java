package phb.ebookstore.dev.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import phb.ebookstore.dev.data.UserV0;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.repository.UserRepository;
import phb.ebookstore.dev.service.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	@Override
	public UserV0 getByEmail(String email) {
		User user = userRepository.findByEmail(email).get();
		return new UserV0(user.getEmail(), user.getRole().name());
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

}
