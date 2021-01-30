package ua.com.alevel.db;

import ua.com.alevel.entity.User;

public interface UserDB extends AbstractDB<User> {
    void delete(Integer id);
}