package ua.com.alevel.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Profile extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String location;
    private String numberPhone;


    public Profile() {
        super();
    }

}
