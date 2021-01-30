package ua.com.alevel.db;

import ua.com.alevel.entity.AbstractEntity;

import java.util.List;

/**
 * @author Iehor Funtusov, created 28/12/2020 - 11:56 AM
 */
public interface AbstractDB<E extends AbstractEntity>{

    void create(E e);
    List<E> read(String fieldName, Object value);
    List<E> readAll();
    void update(E e);

}