package com.security.config;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum UserRole {

	ADMIN(Sets.newHashSet(UserPermission.STUDENT_READ, UserPermission.STUDENT_WRITE, UserPermission.COURSE_READ,
			UserPermission.COURSE_WRITE)),

	ADMIN_TRAINEE(Sets.newHashSet(UserPermission.STUDENT_READ, UserPermission.COURSE_READ)),

	STUDENT(new HashSet<>());

	private final Set<UserPermission> permissions;

	private UserRole(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<UserPermission> getPermissions() {
		return permissions;
	}

	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {

		// Add Permission
		Set<SimpleGrantedAuthority> perms = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());

		// Add Role
		perms.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

		return perms;
	}
}
