package ua.com.alevel.db.impl;


import lombok.SneakyThrows;

import ua.com.alevel.db.UserDB;
import ua.com.alevel.entity.User;
import ua.com.alevel.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Iehor Funtusov, created 28/12/2020 - 12:06 PM
 */

public class UserDBImpl implements UserDB {

    private static UserDBImpl instance;
    private final List<User> users = new ArrayList<>();
    private final Set<Field> fields;

    private UserDBImpl() {

        fields = ReflectionUtil.getAllFieldsByClass(User.class);
    }

    public static UserDBImpl getInstance() {
        if (instance == null) {
            instance = new UserDBImpl();
        }
        return instance;
    }

    @Override
    public void create(User user) {
        int size = users.size();
        ++size;
        user.setId(size);
        users.add(user);
    }

    @SneakyThrows
    @Override
    public List<User> read(String fieldName, Object value) {
        List<User> existUsers = new ArrayList<>();
        for (User user : users) {
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    Object val = field.get(user);
                    if (value.equals(val)) {
                        existUsers.add(user);
                    }
                }
            }
        }

        return existUsers;
    }

    @Override
    public List<User> readAll() {
        return users;
    }

    @Override
    public void update(User user) {
        List<User> users = read("id", user.getId());
        User currentUser = users.get(0);
        currentUser.setAge(user.getAge());
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
    }

    @Override
    public void delete(Integer id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}
