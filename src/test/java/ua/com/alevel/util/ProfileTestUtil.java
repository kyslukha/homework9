package ua.com.alevel.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.experimental.UtilityClass;
@UtilityClass

public class ProfileTestUtil {
    public final String NEW_PHONE = "22222222222";
    public final String CREATE_PHONE = "0123456789";
    public final String UPDATE_PHONE = "9876543210";
    public final String FIELD_PHONE = "numberPhone";
    public final int COLLECTION_SIZE1 = 7;
    public final String FIELD_ID = "id";

    private final List<String> location = new ArrayList<>() {{
        add("Kyiv");
        add("Kharkiv");
        add("Lviv");
        add("Odesa");
        add("Dnipro");
        add("Poltava");
        add("Donetsk");
    }};
    public String getRandomLocation() {
        Random random = new Random();
        return location.get(random.nextInt(location.size()));
    }
}