package ntp.dev.service.imlp;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ntp.dev.data.UserV0;
import ntp.dev.entity.User;
import ntp.dev.repository.UserRepository;

import ntp.dev.service.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImlp implements UserService {

	private final UserRepository userRepository;
	
	@Override
	public UserV0 getByEmail(String email) {
		User user = userRepository.findByEmail(email).get();
		return new UserV0(user.getEmail(), user.getRole().name());
	}

}
