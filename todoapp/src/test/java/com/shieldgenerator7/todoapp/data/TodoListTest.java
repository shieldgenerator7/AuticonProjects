package com.shieldgenerator7.todoapp.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoListTest {

    TodoList todoList;
    Item item = new Item("buy eggs");

    @BeforeEach
    void setUp() {
        todoList = new TodoList();
    }

    @Test
    void add() {
        assertEquals(0, todoList.getCount());
        todoList.add(item);
        assertEquals(1, todoList.getCount());
    }

    @Test
    void getTodos() {
        List<Item> items = todoList.getTodos();
        assertEquals(0, items.size());
        todoList.add(item);
        items = todoList.getTodos();
        assertEquals(1, items.size());
        assertNotNull(items.get(0));
        assertEquals("buy eggs", items.get(0).getHeader());
    }

    @Test
    void getCount() {
        assertEquals(0, todoList.getCount());
    }

    @Test
    void getItem() {
        assertThrows(IndexOutOfBoundsException.class, ()->todoList.getItem(0));
        todoList.add(item);
        Item item1 = todoList.getItem(0);
        assertNotNull(item1);
        assertEquals("buy eggs", item1.getHeader());
    }

    @Test
    void getItemById() {
        todoList.add(item);
        Item item1 = todoList.getItemById(1L);
        assertNotNull(item1);
        assertEquals("buy eggs", item1.getHeader());
    }
}