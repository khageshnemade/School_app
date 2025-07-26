package space.khagesh.school.serviceimpl;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.khagesh.school.entity.Student;
import space.khagesh.school.entity.Teacher;
import space.khagesh.school.entity.User;
import space.khagesh.school.enums.Role;
import space.khagesh.school.repository.StudentRepository;
import space.khagesh.school.repository.TeacherRepository;
import space.khagesh.school.repository.UserRepository;
import space.khagesh.school.service.AuthService;
import space.khagesh.school.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public String registerStudent(String username, String password, String name) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.STUDENT)
                .build();
        userRepository.save(user);

        Student student = Student.builder().name(name).build();
        studentRepository.save(student);

        return "Student registered successfully";
    }

    @Override
    public String registerTeacher(String username, String password, String name) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.TEACHER)
                .build();
        userRepository.save(user);

        Teacher teacher = Teacher.builder().name(name).build();
        teacherRepository.save(teacher);

        return "Teacher registered successfully";
    }

    @Override
    public Map<String, String> login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String name;
        Student s;
        if (user.getRole() == Role.STUDENT) {
            name = studentRepository.findById(user.getId())
                    .map(Student::getName)
                    .orElse("Student");
          
        } else if (user.getRole() == Role.TEACHER) {
            name = teacherRepository.findById(user.getId())
                    .map(Teacher::getName)
                    .orElse("Teacher");
         
        } else {
            name = "Unknown";
        }

        return Map.of(
            "token", token,
            "role", user.getRole().name(),
            "name", name
        );
    }

}
	