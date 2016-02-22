package com.task.satu.rabbitsfarmer.Data;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Satu on 2016-02-18.
 */
@Setter
@Getter
public class Rabbit implements Serializable {
    private String name;
    private String Id;
    private String color;
    private Date birthday;

    public Rabbit(Date birthday, String color, String name) {
        this.birthday = birthday;
        this.color = color;
        this.name = name;
    }

    public Rabbit(String id,String name, Date birthday, String color ) {
        this.birthday = birthday;
        this.color = color;
        Id = id;
        this.name = name;
    }
}
