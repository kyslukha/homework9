package ua.com.alevel;

import org.apache.commons.collections.CollectionUtils;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import ua.com.alevel.db.impl.UserDBImpl;
import ua.com.alevel.entity.User;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static ua.com.alevel.util.UserTestUtil.*;

/**
 * @author Iehor Funtusov, created 28/12/2020 - 2:46 PM
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDBTest implements AbstractCrudTest<User> {

    @BeforeAll
    public static void init() {
        for (int i = 0; i < COLLECTION_SIZE; i++) {
            int age = ThreadLocalRandom.current().nextInt(MIN_AGE, MAX_AGE + 1);
            String name = "test" + i;
            String email = "test" + i + "@gmail.com";
            User user = new User();
            user.setAge(age);
            user.setName(name);
            user.setEmail(email);
            UserDBImpl.getInstance().create(user);
        }
        List<User> users = UserDBImpl.getInstance().readAll();
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        Assert.assertEquals(users.size(), COLLECTION_SIZE);

    }

    @AfterAll
    public static void destroy() {
        List<Integer> usersIdList = UserDBImpl.getInstance().readAll().stream().map(User::getId).collect(Collectors.toList());
        Assert.assertTrue(CollectionUtils.isNotEmpty(usersIdList));
        for (Integer id : usersIdList) {
            UserDBImpl.getInstance().delete(id);
        }
        Assert.assertTrue(CollectionUtils.isEmpty(UserDBImpl.getInstance().readAll()));
    }

    @Test
    @Order(2)
    @Override
    public void create() {
        User user = new User();
        user.setEmail(CREATE_EMAIL);
        UserDBImpl.getInstance().create(user);
        List<User> users = UserDBImpl.getInstance().readAll();
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        Assert.assertEquals(users.size(), COLLECTION_SIZE + 1);
    }

    @Test
    @Order(1)
    @Override
    public void read() {
        List<User> users = UserDBImpl.getInstance().readAll();
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        Assert.assertEquals(users.size(), COLLECTION_SIZE);
        users = UserDBImpl.getInstance().read(FIELD_ID, 1);
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        Assert.assertEquals(users.size(), 1);
    }

    @Test
    @Order(3)
    @Override
    public void update() {
        List<User> users = UserDBImpl.getInstance().read(FIELD_EMAIL, CREATE_EMAIL);
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        Assert.assertEquals(users.size(), 1);
        User user = users.get(0);
        user.setEmail(UPDATE_EMAIL);
        UserDBImpl.getInstance().update(user);
        users = UserDBImpl.getInstance().read(FIELD_EMAIL, UPDATE_EMAIL);
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        Assert.assertEquals(users.size(), 1);
    }

    @Test
    @Order(4)
    @Override
    public void delete() {
        List<User> users = UserDBImpl.getInstance().read(FIELD_EMAIL, UPDATE_EMAIL);
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        Assert.assertEquals(users.size(), 1);
        UserDBImpl.getInstance().delete(users.get(0).getId());
        users = UserDBImpl.getInstance().read(FIELD_EMAIL, UPDATE_EMAIL);
        Assert.assertTrue(CollectionUtils.isEmpty(users));
    }
}
