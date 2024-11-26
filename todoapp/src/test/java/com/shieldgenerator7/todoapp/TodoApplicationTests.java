package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import com.shieldgenerator7.todoapp.data.TodoList;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoApplicationTests {

    final String taskHeader = "buy groceries";

    @Value(value="${local.server.port]")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testAddItem() {
        TodoList list = new TodoList();
        assertEquals(0, list.getCount());
        Item item = new Item(taskHeader);
        assertEquals(taskHeader, item.getHeader());

        list.add(item);
        assertEquals(1, list.getCount());
        assertEquals(taskHeader, list.getItem(0).getHeader());
    }

    @Test
    void testController() {
        TodoController controller = new TodoController();
        List<String> todos = controller.getTodos();
        assertNotNull(todos);
        assertEquals(0, todos.size());

        String addedTodo = controller.addTodo(taskHeader);
        assertEquals(taskHeader, addedTodo);
        todos = controller.getTodos();
        assertNotNull(todos);
        assertEquals(1, todos.size());
        assertEquals(taskHeader, todos.get(0));
    }

}
