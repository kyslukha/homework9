package ua.com.alevel.dao;


import ua.com.alevel.entity.AbstractEntity;
import ua.com.alevel.entity.Profile;
import ua.com.alevel.entity.User;

import java.util.List;

/**
 * @author Iehor Funtusov, created 28/12/2020 - 11:53 AM
 */

public interface AbstractDao<E extends AbstractEntity> {

    void create(E e);

    void update(E e);

}
