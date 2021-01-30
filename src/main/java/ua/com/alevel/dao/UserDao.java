package ua.com.alevel.dao;

import ua.com.alevel.entity.User;

import java.util.List;

/**
 * @author Iehor Funtusov, created 28/12/2020 - 11:53 AM
 */

public interface UserDao extends AbstractDao<User> {

    List<User> readByEmail(String email);
    List<User> read();
    List<User> read(Integer id);
    void delete(Integer id);

}