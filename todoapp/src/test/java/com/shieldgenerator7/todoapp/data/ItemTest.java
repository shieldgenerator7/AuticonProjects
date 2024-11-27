package com.shieldgenerator7.todoapp.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    Item item;

    @BeforeEach
    void setUp() {
        item = new Item("buy eggs");
    }

    @Test
    void getHeader() {
        assertEquals("buy eggs", item.getHeader());
    }

    @Test
    void getCompletionStatus() {
        assertEquals(0, item.getCompletionStatus());
    }

    @Test
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
}