package ua.com.alevel.entity;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class User extends AbstractEntity {

    private String name;
    private String email;
    private int age;

    public User() {
        super();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "created='" + getCreated() + '\'' +

        "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
