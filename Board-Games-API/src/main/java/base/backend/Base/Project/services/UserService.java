package base.backend.Base.Project.services;

import base.backend.Base.Project.models.User;
import base.backend.Base.Project.models.dto.UserDTO;
import base.backend.Base.Project.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User authenticate(String email, String password) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(org.apache.commons.codec.digest.DigestUtils.sha256Hex(password))) {
                return user;
            }
        }

        return null;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User(userDTO);
        user.setDateOfRegistration(LocalDate.now());
        user.setPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Integer id, Map<String, Object> updates) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(this::userNotFound);

        if (updates.containsKey("email")) {
            existingUser.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("username")) {
            existingUser.setUsername((String) updates.get("username"));
        }

        if (updates.containsKey("password")) {
            String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex((String) updates.get("password"));
            existingUser.setPassword(hashedPassword);
        }

        if (updates.containsKey("role")) {
            existingUser.setRole(User.UserRole.valueOf((String) updates.get("role")));
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw userNotFound();
        }
        userRepository.deleteById(id);
    }

    private ResponseStatusException userNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
}
