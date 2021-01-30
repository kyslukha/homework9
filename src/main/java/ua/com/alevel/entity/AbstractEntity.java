package ua.com.alevel.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public abstract class AbstractEntity {

    private Integer id;
    private Date created;

    public AbstractEntity() {
        this.created = new Date();
    }
}
