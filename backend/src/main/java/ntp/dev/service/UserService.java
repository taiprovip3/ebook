package ntp.dev.service;

import ntp.dev.data.UserV0;
import ntp.dev.entity.User;

public interface UserService {
	public UserV0 getByEmail(String email);
	public User getUserByEmail(String email);
}
