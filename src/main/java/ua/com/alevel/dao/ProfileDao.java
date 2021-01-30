package ua.com.alevel.dao;
import ua.com.alevel.entity.Profile;

import java.util.List;

/**
 * @author Iehor Funtusov, created 28/12/2020 - 11:53 AM
 */

public interface ProfileDao extends AbstractDao<Profile> {

    List<Profile> readByNumberPhone(String numberPhone);

    List<Profile> read();

    List<Profile> read(String numberPhone);

    void delete(String numberPhone);
}