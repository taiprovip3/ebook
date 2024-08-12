package phb.ebookstore.dev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserV0 getByEmail(String email) {
		User user = userRepository.findByEmail(email).get();
		return new UserV0(user.getEmail(), user.getRole().name());
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		User user = userRepository.findByEmail(username).orElse(null);
		if(user != null && passwordEncoder.matches(oldPassword, user.getPassword())) {
			user.setPass_word(passwordEncoder.encode(newPassword));
			userRepository.save(user);
			return true;
		}
		return false;
	}

}
