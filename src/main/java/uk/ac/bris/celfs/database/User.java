package uk.ac.bris.celfs.database;

import java.util.Set;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "teacher")
@Data
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotEmpty
    private String username;
    
    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    private User() {

    }
    
    public User(String firstName, String lastName) {
        this.username = firstName;
        this.lastName = lastName;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getFirstName() {
        return username;
    }
    
    public String getLastName() {
        return lastName;
    }
}