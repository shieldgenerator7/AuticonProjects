package com.shieldgenerator7.todoapp.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    private String header;

    @Getter
    @ColumnDefault("0")
    private int completionStatus = 0;

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
    }

    @Getter
    @Setter
    @ColumnDefault("0")
    private Priority priority;

    public Item() {
        header = "";
        priority = Priority.LOW;
    }

    public Item(String header) {
        this.header = header;
        priority = Priority.LOW;
    }

    public Long getId() {
        return id;
    }

    public int setCompletionStatus(int value) {
        completionStatus = Math.min(Math.max(value, 0), 100);
        return completionStatus;
    }

    public void _setId(long l) {
        this.id = l;
    }
}
