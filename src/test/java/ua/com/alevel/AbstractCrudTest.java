package ua.com.alevel;

import ua.com.alevel.entity.AbstractEntity;

/**
 * @author Iehor Funtusov, created 28/12/2020 - 12:31 PM
 */

public interface AbstractCrudTest<E extends AbstractEntity> {

    void create();
    void read();
    void update();
    void delete();
}