package ua.com.alevel.db.impl;
import lombok.SneakyThrows;


import ua.com.alevel.db.ProfileDB;
import ua.com.alevel.entity.Profile;
import ua.com.alevel.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ProfileDBImpl implements ProfileDB {
    private static ProfileDBImpl instance;
    private final List<Profile> profiles = new ArrayList<>();
    private final Set<Field> fields;

    private ProfileDBImpl() {

        fields = ReflectionUtil.getAllFieldsByClass(Profile.class);
    }

    public static ProfileDBImpl getInstance() {
        if (instance == null) {
            instance = new ProfileDBImpl();
        }
        return instance;
    }

    @Override
    public void create(Profile profile) {
        int size = profiles.size();
        ++size;
        profile.setId(size);
        profiles.add(profile);
    }

    @SneakyThrows
    @Override
    public List<Profile> read(String fieldName, Object value) {
        List<Profile> existProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    Object val = field.get(profile);
                    if (value.equals(val)) {
                        existProfiles.add(profile);
                    }
                }
            }
        }

        return existProfiles;
    }
    @Override
    public List<Profile> readAll() {
        return profiles;
    }

    @Override
    public void update(Profile profile) {
        List<Profile> profiles = read("id", profile.getId());
        Profile currentProfile = profiles.get(0);
        currentProfile.setFirstName(profile.getFirstName());
        currentProfile.setLastName(profile.getLastName());
        currentProfile.setLocation(profile.getLocation());
        currentProfile.setNumberPhone(profile.getNumberPhone());
    }

    @Override
    public void delete(String numberPhone) {
        profiles.removeIf(profile -> profile.getNumberPhone().equals(numberPhone));
    }
}
