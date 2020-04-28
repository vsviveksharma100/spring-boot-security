package com.security.dataaccess;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.security.config.UserRole;
import com.security.model.User;

@Repository
public class FakeApplicationUserDaoService implements ApplicationUserDao {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Optional<User> loadUserByUsername(String username) {
		return getUsers().stream().filter(user -> username.equals(user.getUsername())).findFirst();
	}

	private List<User> getUsers() {
		return Lists.newArrayList(getUser("annasmith", "password", UserRole.STUDENT),
				getUser("linda", "password", UserRole.ADMIN), getUser("tom", "password", UserRole.ADMIN_TRAINEE));
	}

	private User getUser(String username, String password, UserRole role) {
		return new User(username, passwordEncoder.encode(password), role.getGrantedAuthorities(), true, true, true,
				true);
	}
}
