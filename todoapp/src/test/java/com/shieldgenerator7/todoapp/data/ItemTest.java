package com.shieldgenerator7.todoapp.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}