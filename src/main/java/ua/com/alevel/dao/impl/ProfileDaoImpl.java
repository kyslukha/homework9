package ua.com.alevel.dao.impl;


import ua.com.alevel.dao.ProfileDao;
import ua.com.alevel.db.impl.ProfileDBImpl;
import ua.com.alevel.entity.Profile;

import java.util.List;

public class ProfileDaoImpl implements ProfileDao {

    ProfileDBImpl db = ProfileDBImpl.getInstance();

    public List<Profile> readByNumberPhone(String numberPhone) {

        return db.read("numberPhone", numberPhone);
    }

    @Override
    public void create(Profile profile) {

        db.create(profile);
    }

    @Override
    public List<Profile> read() {

        return db.readAll();
    }

    public List<Profile> read(String numberPhone) {

        return db.read("numberPhone", numberPhone);
    }

    @Override
    public void update(Profile profile) {

        db.update(profile);
    }

    public void delete(String numberPhone) {

        db.delete(numberPhone);
    }
}