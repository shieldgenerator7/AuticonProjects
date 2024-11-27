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

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testController() {
        String url = "http://localhost:"+port+"/todos";

        //test empty
        String string = this.restTemplate.getForObject(url, String.class);
        assertEquals("[]",string);

        //test add
        String addedTodo = this.restTemplate.postForObject(url, taskHeader, String.class);
        assertEquals(taskHeader, addedTodo);

        string = this.restTemplate.getForObject(url, String.class);
        assertEquals("[\""+taskHeader+"\"]",string);
    }

}
