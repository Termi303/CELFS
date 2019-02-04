package uk.ac.bris.celfs.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import org.jasypt.util.password.*;

@Entity
@Table(name = "user")
@Data
public class User {

    public static BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    @NotEmpty
    private String username;

    private String encryptedPassword;
    private UserType userType;

    private User() {

    }
    
    public User(String firstName, String password) {
        this.username = firstName;
        this.encryptedPassword = passwordEncryptor.encryptPassword(password);
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