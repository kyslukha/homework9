package ua.com.alevel;
import org.apache.commons.collections.CollectionUtils;

import org.junit.jupiter.api.*;


import ua.com.alevel.dao.ProfileDao;
import ua.com.alevel.dao.impl.ProfileDaoImpl;
import ua.com.alevel.db.impl.ProfileDBImpl;
import ua.com.alevel.entity.Profile;


import java.util.List;

import java.util.stream.Collectors;

import static ua.com.alevel.util.ProfileTestUtil.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileDaoTest implements AbstractCrudTest<Profile> {

    private static final ProfileDao profileDao = new ProfileDaoImpl();

    @BeforeAll
    public static void init() {
        for (int i = 0; i < COLLECTION_SIZE1; i++) {
            String firstName = "firstname" + i;
            String lastName = "lastname" + i;
            String numberPhone = NEW_PHONE + i;
            String location = getRandomLocation();
            Profile profile = new Profile();
            profile.setFirstName(firstName);
            profile.setLastName(lastName);
            profile.setNumberPhone(numberPhone);
            profile.setLocation(location);
            profileDao.create(profile);
        }
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profileDao.read()));
    }

    @AfterAll
    public static void destroy() {
        List<Profile> profiles = profileDao.read();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profiles));
        Assertions.assertEquals(profiles.size(), COLLECTION_SIZE1);


        List<String> profilesPhoneList = profiles.stream().map(Profile::getNumberPhone).collect(Collectors.toList());
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profilesPhoneList));
        for (String numberPhone : profilesPhoneList) {
            ProfileDBImpl.getInstance().delete(numberPhone);
        }
        Assertions.assertTrue(CollectionUtils.isEmpty(profileDao.read()));
    }
    @Test
    @Order(1)
    @Override
    public void read() {
        List<Profile> profilesList = profileDao.read();
        Assertions.assertEquals(profilesList.size(), COLLECTION_SIZE1);
    }

        @Test
    @Order(2)
    @Override
    public void create() {
        Profile profile = new Profile();
        profile.setNumberPhone(CREATE_PHONE);
        profile.setFirstName("New firstname");
        profile.setFirstName("New lastname");
        profile.setLocation("New Location");
        profileDao.create(profile);
        List<Profile> profilesList = profileDao.read();
        Assertions.assertEquals(profilesList.size(),COLLECTION_SIZE1+1);
    }



    @Test
    @Order(3)
    @Override
    public void update() {
        List<Profile> profiles = profileDao.readByNumberPhone(CREATE_PHONE);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profiles));
        Assertions.assertEquals(profiles.size(), 1);
        Profile profile = profiles.get(0);
        profile.setNumberPhone(UPDATE_PHONE);
        profileDao.update(profile);
        profiles = profileDao.readByNumberPhone(UPDATE_PHONE);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profiles));
        Assertions.assertEquals(profiles.size(),1);
    }

    @Test
    @Order(4)
    @Override
    public void delete() {
        List<String> profilesPhoneList = profileDao.read().stream().map(Profile::getNumberPhone).collect(Collectors.toList());
        Assertions.assertEquals(profilesPhoneList.size(),COLLECTION_SIZE1+1);
        for (String numberPhone : profilesPhoneList) {
            if (numberPhone.equals(UPDATE_PHONE)) {
                profileDao.delete(numberPhone);
            }
        }
        List<Profile> profilesList = profileDao.read();
        Assertions.assertEquals(profilesList.size(), COLLECTION_SIZE1);
    }
}