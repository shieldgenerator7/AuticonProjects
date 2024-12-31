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

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Priority priority;

    /**
     * Constructs an Item with empty header and low priority
     */
    public Item() {
        header = "";
        priority = Priority.LOW;
    }

    /**
     * Constructs an Item with the given header and low priority
     * @param header The header for the Item
     */
    public Item(String header) {
        this.header = header;
        priority = Priority.LOW;
    }

    /**
     * Returns the Id of this Item
     * @return the Id of this Item
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the completion status of this Item to the given value, clamped to between 0 and 100, inclusive.
     * @param value The value to set completion status to
     * @return The resulting completion status, between 0 and 100, inclusive.
     */
    public int setCompletionStatus(int value) {
        completionStatus = Math.min(Math.max(value, 0), 100);
        return completionStatus;
    }
}
