package uk.ac.bris.celfs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.bris.celfs.database.User;
import uk.ac.bris.celfs.database.UserRepository;
import uk.ac.bris.celfs.database.UserType;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void addUser(String username, String password, UserType userType) {
        User user = new User(username, password, userType);
        addUser(user);
    }

    private List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        userRepository.findAll()
                .forEach(result::add);
        return result;
    }

    public User getUserFromUsername(String username){
      for(User user : getAllUsers()) {
          if(user.getUsername().equals(username)){
            return user;
          }
        }
      return null;
    }

    // public User getUser(String username, String password) {
    //     for(User user : getAllUsers()) {
    //         if(user.getUsername().equals(username)
    //             && User.passwordEncryptor.checkPassword(password, user.getEncryptedPassword())) {
    //             return user;
    //         }
    //     }
    //     return null;
    // }

    public boolean hasAdminPermissions(User user) {
        return hasGodPermissions(user) || user.getUserType() == UserType.ADMIN;
    }

    public boolean hasGodPermissions(User user) {
        return user.getUserType() == UserType.GOD;
    }

    public void init() {
        int howManyStudents = 5;
        for(int i = 0; i < howManyStudents; i++) {
            int singleId = 12345+i;
            addUser("ab" + singleId, "password", UserType.STUDENT);
        }
        addUser("admin", "admin", UserType.ADMIN);
        int howManyTeachers = 5;
        for(int i = 1; i <= howManyTeachers; i++) {
            addUser("teacher" + i, "teacher", UserType.TEACHER);
        }

    }
}
