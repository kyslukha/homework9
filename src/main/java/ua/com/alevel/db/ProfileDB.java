package ua.com.alevel.db;

import ua.com.alevel.entity.Profile;

public interface ProfileDB extends AbstractDB<Profile> {
    void delete(String numberPhone);
}