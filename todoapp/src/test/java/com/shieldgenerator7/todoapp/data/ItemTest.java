package com.shieldgenerator7.todoapp.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Item should")
class ItemTest {

    Item item;

    @BeforeEach
    void setUp() {
        item = new Item("buy eggs");
    }

    @Test
    @DisplayName("have a header")
    void getHeader() {
        assertEquals("buy eggs", item.getHeader());
    }

    @Test
    @DisplayName("have a completion status")
    void getCompletionStatus() {
        assertEquals(0, item.getCompletionStatus());
    }

    @Test
    @DisplayName("limit the range of completion status")
    void setCompletionStatus() {
        assertEquals(0, item.getCompletionStatus());
        assertAll("Completion Status Test",
                () -> assertEquals(0, item.setCompletionStatus(0)),
                () -> assertEquals(0, item.setCompletionStatus(-1)),
                () -> assertEquals(50, item.setCompletionStatus(50)),
                () -> assertEquals(100, item.setCompletionStatus(100)),
                () -> assertEquals(100, item.setCompletionStatus(101)),
                () -> assertEquals(0, item.setCompletionStatus(0)),
                () -> assertEquals(100, item.setCompletionStatus(100))
        );
    }

    @Test
    @DisplayName("have a priority")
    void setPriority() {
        assertEquals(Item.Priority.LOW, item.getPriority());
        item.setPriority(Item.Priority.HIGH);
        assertEquals(Item.Priority.HIGH, item.getPriority());
    }
}