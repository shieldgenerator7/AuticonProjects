package com.shieldgenerator7.todoapp.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoListTest {

    TodoList todoList;
    Item item;

    @BeforeEach
    void setUp() {
        todoList = new TodoList();
        item = new Item("buy eggs");
        item._setId(1L);
    }

    @Test
    void add() {
        assertEquals(0, todoList.getCount());
        todoList.add(item);
        assertEquals(1, todoList.getCount());
        todoList.add("buy milk");
        assertEquals(2, todoList.getCount());
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
        assertThrows(IndexOutOfBoundsException.class, () -> todoList.getItem(0));
        todoList.add(item);
        Item item1 = todoList.getItem(0);
        assertNotNull(item1);
        assertEquals("buy eggs", item1.getHeader());
    }

    @Test
    void getItemById() {
        todoList.add(item);
        assertEquals(1L, item.getId());
        Item item1 = todoList.getItemById(1L);
        assertNotNull(item1);
        assertEquals("buy eggs", item1.getHeader());
    }

    @Test
    void removeById() {
        todoList.add(item);
        assertEquals(1L, item.getId());
        Item item1 = todoList.getItemById(1L);
        assertNotNull(item1);
        assertEquals("buy eggs", item1.getHeader());

        todoList.removeById(item.getId());
        item1 = todoList.getItemById(1L);
        assertNull(item1);

    }

    @Test
    void validateNonEmpty() {
        assertAll(
                () -> {
                    int count = todoList.getCount();
                    todoList.add("");
                    assertEquals(count, todoList.getCount());
                },
                () -> {//
                    int count = todoList.getCount();
                    todoList.add(" ");
                    assertEquals(count, todoList.getCount());
                },//
                () -> {
                    int count = todoList.getCount();
                    todoList.add((String) null);
                    assertEquals(count, todoList.getCount());
                },
                () -> {
                    int count = todoList.getCount();
                    todoList.add((Item) null);
                    assertEquals(count, todoList.getCount());
                }
        );
    }

    @Test
    void validateMaxLength() {
        assertEquals(0, todoList.getCount());
        //2024-12-04: copied from https://www.lipsum.com/
        assertAll(
                () -> {
                    int count = todoList.getCount();
                    todoList.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
                    assertEquals(count, todoList.getCount());
                },
                () -> {
                    int count = todoList.getCount();
                    todoList.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore ");//100 characters: YES
                    assertEquals(count + 1, todoList.getCount());
                },
                () -> {
                    int count = todoList.getCount();
                    todoList.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore e");//101 characters: NO
                    assertEquals(count, todoList.getCount());
                }
        );
    }

    @Test
    void validateNoDuplicates() {
        assertEquals(0, todoList.getCount());
        todoList.add("buy eggs");
        assertEquals(1, todoList.getCount());
        todoList.add("buy eggs");
        assertEquals(1, todoList.getCount());
    }

    @Test
    void searchItems() {
        List<String> itemHeaders = Arrays.asList(
                "buy eggs",
                "buy milk",
                "take a shower"
        );
        itemHeaders.forEach(header -> todoList.add(header));
        assertEquals(3, todoList.getCount());
        String query = "buy";

        assertAll(
                () -> {
                    //search predicate
                    List<Item> searchResults = todoList.searchItems(item -> item.getHeader().contains(query));
                    assertNotNull(searchResults);
                    assertEquals(2, searchResults.size());
                    assertEquals(itemHeaders.get(0), searchResults.get(0).getHeader());
                    assertEquals(itemHeaders.get(1), searchResults.get(1).getHeader());
                },
                () -> {
                    //search string
                    List<Item> searchResults = todoList.searchItems(query);
                    assertNotNull(searchResults);
                    assertEquals(2, searchResults.size());
                    assertEquals(itemHeaders.get(0), searchResults.get(0).getHeader());
                    assertEquals(itemHeaders.get(1), searchResults.get(1).getHeader());
                }
        );

    }
}