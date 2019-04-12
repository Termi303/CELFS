package uk.ac.bris.celfs.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserTest {
    @Resource
    private UserRepository userRepository;

    @Test
    public void testCreateOneUser() {
        UserType userType = UserType.STUDENT;
        String username = "username";
        String password = "password";

        User user = new User(username, password, userType);
        userRepository.save(user);

        assert(user.getId() != null);
        assertEquals(username, user.getUsername());
        assertEquals(userType, user.getUserType());
        assert(User.passwordEncryptor.matches(password, user.getEncryptedPassword()));
    }

}
