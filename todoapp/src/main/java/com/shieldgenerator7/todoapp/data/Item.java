package com.shieldgenerator7.todoapp.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    private String header;

    @Getter
    private int completionStatus = 0;

    public Item (){
        header = "";
    }

    public Item(String header) {
        this.header = header;
    }

    public Long getId(){
        return id;
    }

    public int setCompletionStatus(int value){
        completionStatus = Math.min(Math.max(value, 0), 100);
        return completionStatus;
    }

    public void _setId(long l) {
        this.id = l;
    }
}
