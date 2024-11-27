package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import com.shieldgenerator7.todoapp.data.TodoList;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        List<Item> itemList = this.restTemplate.getForObject(url, List.class);
        assertEquals(1,itemList.size());
    }

    @Test
    void testCompletionStatus(){
        String url = "http://localhost:"+port+"/todos";
        String urlIds = "http://localhost:"+port+"/todoIds";
        String urlItem = "http://localhost:"+port+"/item";


        String idList = this.restTemplate.getForObject(urlIds, String.class);
        assertEquals("[1]",idList);//from previous test
        int itemId = Integer.parseInt(
                (String)Arrays.stream(idList.split("[,\\[\\]]"))
                .filter(
                        a->a.trim().length()>0
                ).toArray()[0]
        );
        assertEquals(1,itemId);

        Item item = this.restTemplate.getForObject(urlItem, Item.class, Map.of("id",itemId));
        assertNotNull(item);
        assertEquals(1, item.getId());

    }

}
