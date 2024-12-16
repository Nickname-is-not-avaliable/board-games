package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "Entity representing a user")
public class User {
    public User(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.role = userDTO.getRole();
        this.username = userDTO.getUsername();
        this.dateOfRegistration = userDTO.getDateOfRegistration();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    private String email;
    private String username;
    private String password;
    private LocalDate dateOfRegistration;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public enum UserRole {
        USER, ADMIN
    }
}
