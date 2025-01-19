package service;

import java.util.List;

import dao.UserDAO;
import model.User;
import model.LoginDTO;
import model.RegisterDTO;
import shared.security.SecretHasher;
import shared.service.ServiceDeletable;
import shared.service.ServiceBulkable;
import shared.service.ServiceSelectable;
import shared.service.ServiceSavable;
import shared.service.ServiceUpdatable;

public class UserService implements 
	ServiceBulkable, 
	ServiceSelectable, 
	ServiceDeletable, 
	ServiceUpdatable, 
	ServiceSavable {

	private final UserDAO dao;
	private final SecretHasher hasher;

	public UserService(UserDAO dao) {
		this.dao		= dao;
		this.hasher = new SecretHasher(16, 65536, 64, 2);
	}

	public List<User> fetchAll() throws RuntimeException {
		return ServiceBulkable.super.bulk(this.dao, (dao) -> dao.fetchAll());
	}

	public User get(int id) throws RuntimeException {
		return ServiceSelectable.super.get(this.dao, (dao) -> dao.get(id));
	}

	public User getUserWithUsername(String username) throws RuntimeException {
		return ServiceSelectable.super.get(this.dao, (dao) -> dao.getUserWithUsername(username));
	}

	public User getUserWithEmail(String email) throws RuntimeException {
		return ServiceSelectable.super.get(this.dao, (dao) -> dao.getUserWithEmail(email));
	}


	public boolean validateCredentials(LoginDTO ulogin) throws RuntimeException {
		if(ulogin.getUsername() == null || ulogin.getUsername().isBlank())
			throw new IllegalArgumentException("Missing username for user, cannot perform insert!");
		if(ulogin.getPassword() == null || ulogin.getPassword().isBlank())
			throw new IllegalArgumentException("Missing username for user, cannot perform insert!");

		User user = getUserWithUsername(ulogin.getUsername());
		return user != null && user.getSecret()
			.equals(this.hasher.hashSecretWithSalt(ulogin.getPassword(), user.getSalt()));
	}

	public boolean delete(int id) throws RuntimeException {
		return ServiceDeletable.super.delete(this.dao, (dao) -> dao.delete(id));
	}

	public boolean save(User u) throws RuntimeException, IllegalArgumentException {
		if(u.getUsername() == null || u.getUsername().isBlank())
			throw new IllegalArgumentException("Missing username for user, cannot perform insert!");

		// Default role to user
		if(u.getRole() == null || u.getRole().isBlank())
			u.setRole("user");

		if(u.getEmail() == null || u.getEmail().isBlank())
			throw new IllegalArgumentException("Missing email for user, cannot perform insert!");

		if(u.getSalt() == null || u.getSalt().isBlank())
			throw new IllegalArgumentException("Missing salt for user, cannot perform insert!");

		if(u.getSecret() == null || u.getSecret().isBlank())
			throw new IllegalArgumentException("Missing secret for user, cannot perform insert!");

		return ServiceSavable.super.save(this.dao, (dao) -> dao.save(u));
	}

	public boolean saveNewUser(RegisterDTO dto) throws RuntimeException, IllegalArgumentException {
		if(dto.getUsername() == null || dto.getUsername().isBlank())
			throw new IllegalArgumentException("Missing username for user, cannot perform insert!");

		if(dto.getEmail() == null || dto.getEmail().isBlank())
			throw new IllegalArgumentException("Missing email for user, cannot perform insert!");

		if(dto.getPassword() == null || dto.getPassword().isBlank())
			throw new IllegalArgumentException("Missing password for user, cannot perform insert!");

		// Username or Email already exists
		if(getUserWithUsername(dto.getUsername()) != null || getUserWithEmail(dto.getEmail()) != null) {
			return false;
		}

		User u = new User();
			u.setUsername(dto.getUsername());
			u.setRole("user");
			u.setEmail(dto.getEmail());
			u.setSalt(hasher.generateSalt16Byte());
			u.setSecret(hasher.hashSecretWithSalt(dto.getPassword(), u.getSalt()));
		return this.save(u);
	}


	public boolean update(User u) throws RuntimeException {
		if(u.getId() == null)
			throw new IllegalArgumentException("Missing id for user, cannot perform update!");

		if(u.getUsername() == null || u.getUsername().isBlank())
			throw new IllegalArgumentException("Missing username for user, cannot perform update!");

		if(u.getRole() == null || u.getRole().isBlank())
			throw new IllegalArgumentException("Missing secret for user, cannot perform update!");

		if(u.getEmail() == null || u.getEmail().isBlank())
			throw new IllegalArgumentException("Missing email for user, cannot perform update!");

		if(u.getSalt() == null || u.getSalt().isBlank())
			throw new IllegalArgumentException("Missing salt for user, cannot perform update!");

		if(u.getSecret() == null || u.getSecret().isBlank())
			throw new IllegalArgumentException("Missing secret for user, cannot perform update!");

		return ServiceUpdatable.super.update(this.dao, (dao) -> dao.update(u));
	}

}
