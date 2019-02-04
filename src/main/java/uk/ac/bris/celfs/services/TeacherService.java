package uk.ac.bris.celfs.services;

import uk.ac.bris.celfs.database.UserRepository;
import uk.ac.bris.celfs.database.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private UserRepository userRepository;

    public void add(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll()
                .forEach(users::add);
        return users;
    }

    public User getAny() {
        return getAll().get(0);
    }

    public void init() {
        int howMany = 2;
        for(int i = 0; i < howMany; i++) {
            add(new User("TeacherFirst " + i, "TeacherLast " + i));
        }
    }

}