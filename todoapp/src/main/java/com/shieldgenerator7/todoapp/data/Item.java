package com.shieldgenerator7.todoapp.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String header;

    public Item (){
        header = "";
    }

    public Item(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

}
