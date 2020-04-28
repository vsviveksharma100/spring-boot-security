package com.security.dataaccess;

import java.util.Optional;

import com.security.model.User;

public interface ApplicationUserDao {

	public Optional<User> loadUserByUsername(String username);
}
