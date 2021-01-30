package ua.com.alevel;

import org.apache.commons.collections.CollectionUtils;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import ua.com.alevel.dao.UserDao;
import ua.com.alevel.dao.impl.UserDaoImpl;
import ua.com.alevel.db.impl.UserDBImpl;
import ua.com.alevel.entity.User;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static ua.com.alevel.util.UserTestUtil.*;

/**
 * @author Iehor Funtusov, created 28/12/2020 - 12:31 PM
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoTest implements AbstractCrudTest<User> {

    private static final UserDao userDao = new UserDaoImpl();

    @BeforeAll
    public static void init() {
        for (int i = 0; i < 10; i++) {
            int age = ThreadLocalRandom.current().nextInt(MIN_AGE, MAX_AGE + 1);
            String name = "test" + i;
            String email = "test" + i + "@gmail.com";
            User user = new User();
            user.setAge(age);
            user.setName(name);
            user.setEmail(email);
            userDao.create(user);
        }
        Assert.assertTrue(CollectionUtils.isNotEmpty(userDao.read()));
    }

    @AfterAll
    public static void destroy() {
        List<User> users = userDao.read();
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        Assertions.assertEquals(users.size(), 2);
        List<Integer> usersIdList = users.stream().map(User::getId).collect(Collectors.toList());
        Assert.assertTrue(CollectionUtils.isNotEmpty(usersIdList));
        for (Integer id : usersIdList) {
            UserDBImpl.getInstance().delete(id);
        }
        Assert.assertTrue(CollectionUtils.isEmpty(userDao.read()));
    }

    @Test
    @Order(2)
    @Override
    public void create() {
        User user = new User();
        user.setName("test123");
        user.setEmail(CREATE_EMAIL);
        user.setAge(35);
        userDao.create(user);
        List<User> usersList = userDao.read();
        Assert.assertEquals(usersList.size(), 11);
    }

    @Test
    @Order(1)
    @Override
    public void read() {
        List<User> usersList = userDao.read();
        Assert.assertEquals(usersList.size(), 10);
    }

    @Test
    @Order(3)
    @Override
    public void update() {
        List<User> users = userDao.readByEmail(CREATE_EMAIL);
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        Assert.assertEquals(users.size(), 1);
        User user = users.get(0);
        user.setEmail(UPDATE_EMAIL);
        userDao.update(user);
        users = userDao.readByEmail(UPDATE_EMAIL);
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        Assert.assertEquals(users.size(), 1);
    }

    @Test
    @Order(4)
    @Override
    public void delete() {
        List<Integer> usersIdList = userDao.read().stream().map(User::getId).collect(Collectors.toList());
        Assert.assertEquals(usersIdList.size(), 11);
        for (Integer id : usersIdList) {
            if (id > 2) {
                userDao.delete(id);
            }
        }
        List<User> usersList = userDao.read();
        Assert.assertEquals(usersList.size(), 2);
    }
}
