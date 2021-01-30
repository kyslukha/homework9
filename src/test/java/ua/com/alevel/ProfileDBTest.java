package ua.com.alevel;

import org.apache.commons.collections.CollectionUtils;
import ua.com.alevel.db.impl.ProfileDBImpl;
import ua.com.alevel.entity.Profile;

import org.junit.jupiter.api.*;
import java.util.List;

import java.util.stream.Collectors;

import static ua.com.alevel.util.ProfileTestUtil.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileDBTest implements AbstractCrudTest<Profile>{

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
            ProfileDBImpl.getInstance().create(profile);
        }
        List<Profile> profiles = ProfileDBImpl.getInstance().readAll();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profiles));
        Assertions.assertEquals(profiles.size(), COLLECTION_SIZE1);
    }

    @AfterAll
    public static void destroy() {
        List<String> profilesPhoneList = ProfileDBImpl.getInstance().readAll().stream().map(Profile::getNumberPhone).collect(Collectors.toList());
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profilesPhoneList));
        for (String numberPhone : profilesPhoneList) {
            ProfileDBImpl.getInstance().delete(numberPhone);
        }
        Assertions.assertTrue(CollectionUtils.isEmpty(ProfileDBImpl.getInstance().readAll()));
    }


    @Test
    @Order(1)
    @Override
    public void read() {
        List<Profile> profiles = ProfileDBImpl.getInstance().readAll();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profiles));
        Assertions.assertEquals(profiles.size(), COLLECTION_SIZE1);
        profiles = ProfileDBImpl.getInstance().read(FIELD_ID, 1);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profiles));
        Assertions.assertEquals(profiles.size(), 1);
    }

    @Test
    @Order(2)
    @Override
    public void create() {
        Profile profile = new Profile();
        profile.setNumberPhone(CREATE_PHONE);
        ProfileDBImpl.getInstance().create(profile);
        List<Profile> profiles = ProfileDBImpl.getInstance().readAll();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profiles));
        Assertions.assertEquals(profiles.size(), COLLECTION_SIZE1+1);
    }



    @Test
    @Order(3)
    @Override
    public void update() {
        List<Profile> profiles = ProfileDBImpl.getInstance().read(FIELD_PHONE, CREATE_PHONE);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profiles));
        Assertions.assertEquals(profiles.size(), 1);
        Profile profile = profiles.get(0);
        profile.setNumberPhone(UPDATE_PHONE);
        ProfileDBImpl.getInstance().update(profile);
        profiles = ProfileDBImpl.getInstance().read(FIELD_PHONE, UPDATE_PHONE);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profiles));
        Assertions.assertEquals(profiles.size(), 1);
    }

    @Test
    @Order(4)
    @Override
    public void delete() {
        List<Profile> profiles = ProfileDBImpl.getInstance().read(FIELD_PHONE, UPDATE_PHONE);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(profiles));
        Assertions.assertEquals(profiles.size(), 1);
        ProfileDBImpl.getInstance().delete(UPDATE_PHONE);
        profiles = ProfileDBImpl.getInstance().read(FIELD_PHONE, UPDATE_PHONE);
        Assertions.assertTrue(CollectionUtils.isEmpty(profiles));
    }
}
