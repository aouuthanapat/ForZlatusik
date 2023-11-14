package com.example.apiapplication.Models;

import java.io.Serializable;

public class Source implements Serializable {
    // тут из БД Postman реализован класс Source с переменными id и name
    String id = "";
    String name = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
