package com.security.config;

public enum UserPermission {
	STUDENT_READ("student:read"), STUDENT_WRITE("student:write"), COURSE_WRITE("course:write"),
	COURSE_READ("course:read"),;

	private final String permission;

	UserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}
