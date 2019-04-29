package uk.ac.bris.celfs.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import org.jasypt.util.password.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "user")
@Data
public class User {

    public static BCryptPasswordEncoder passwordEncryptor = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    @NotEmpty
    private String username;

    private String encryptedPassword;
    private UserType userType;

    private User() {

    }

    public User(String firstName, String password, UserType userType) {
        this.username = firstName;
        this.encryptedPassword = passwordEncryptor.encode(password);
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public UserType getUserType() {
        return userType;
    }
}
