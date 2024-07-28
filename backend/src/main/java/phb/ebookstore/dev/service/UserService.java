package phb.ebookstore.dev.service;

import phb.ebookstore.dev.data.UserV0;
import phb.ebookstore.dev.entity.User;

public interface UserService {
	public UserV0 getByEmail(String email);
	public User getUserByEmail(String email);
}
