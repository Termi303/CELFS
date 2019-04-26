package uk.ac.bris.celfs.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.database.Keyword;
import uk.ac.bris.celfs.database.User;
import uk.ac.bris.celfs.database.UserType;

import static org.junit.Assert.assertEquals;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    protected UserService userService;

    @Test
    public void testAddUserByObject() {
        User user = new User("TEST_NAME_0", "TEST_PASSWORD_0", UserType.TEACHER);
        userService.addUser(user);

        assertEquals(user, userService.getUserFromUsername("TEST_NAME_0"));
    }

    @Test
    public void testAddUserByElements() {
        userService.addUser("TEST_NAME_1", "TEST_PASSWORD_1", UserType.TEACHER);

        User user = userService.getUserFromUsername("TEST_NAME_1");
        assertEquals("TEST_NAME_1", user.getUsername());
        assertEquals(UserType.TEACHER, user.getUserType());
    }

    @Test
    public void testUserTypePermissions() {
        User user1 = new User("TEST_NAME_2", "TEST_PASSWORD_2", UserType.TEACHER);
        User user2 = new User("TEST_NAME_3", "TEST_PASSWORD_3", UserType.GOD);
        User user3 = new User("TEST_NAME_4", "TEST_PASSWORD_4", UserType.ADMIN);

        assertEquals(false, userService.hasAdminPermissions(user1));
        assertEquals(false, userService.hasGodPermissions(user1));
        assertEquals(true, userService.hasAdminPermissions(user2));
        assertEquals(true, userService.hasGodPermissions(user2));
        assertEquals(true, userService.hasAdminPermissions(user3));
        assertEquals(false, userService.hasGodPermissions(user3));
    }
}
