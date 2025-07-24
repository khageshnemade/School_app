package space.khagesh.school.service;

import java.util.Map;

public interface AuthService {
	String registerStudent(String username, String password, String name);

	String registerTeacher(String username, String password, String name);

	Map<String, String> login(String username, String password);
}