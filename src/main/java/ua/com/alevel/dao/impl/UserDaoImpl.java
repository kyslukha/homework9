package ua.com.alevel.dao.impl;


import ua.com.alevel.dao.UserDao;
import ua.com.alevel.db.impl.UserDBImpl;
import ua.com.alevel.entity.User;

import java.util.List;

/**
 * @author Iehor Funtusov, created 28/12/2020 - 11:54 AM
 */
public class UserDaoImpl implements UserDao {

    UserDBImpl db = UserDBImpl.getInstance();

    @Override
    public List<User> readByEmail(String email) {
        return db.read("email", email);
    }

    @Override
    public void create(User user) {
        db.create(user);
    }

    @Override
    public List<User> read() {
        return db.readAll();
    }

    @Override
    public List<User> read(Integer id) {
        return db.read("id", id);
    }

    @Override
    public void update(User user) {
        db.update(user);
    }

    @Override
    public void delete(Integer id) {
        db.delete(id);
    }
}