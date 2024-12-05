package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import com.shieldgenerator7.todoapp.data.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoApplicationTests {

    final String taskHeader = "buy groceries";

    String baseURL;

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private Item item;

    @BeforeEach
    void initTests(){
        baseURL = "http://localhost:" + port;
    }

    @Test
    void testController() {
        String url = baseURL + "/todos";

        //test empty
        String string = this.restTemplate.getForObject(url, String.class);
        assertEquals("[]", string);

        //test add
        String addedTodo = this.restTemplate.postForObject(url, taskHeader, String.class);
        assertEquals(taskHeader, addedTodo);

        List<Item> itemList = this.restTemplate.getForObject(url, List.class);
        assertEquals(1, itemList.size());
    }

    @Test
    void testCompletionStatus() {
        String url = baseURL + "/todos";
        String urlIds = baseURL + "/todoIds";
        String urlItem = baseURL + "/item";

        String addedTodo = this.restTemplate.postForObject(url, taskHeader, String.class);

        String idList = this.restTemplate.getForObject(urlIds, String.class);
        assertEquals("[2]", idList);//from previous test
        Long itemId = Long.parseLong(
                (String) Arrays.stream(idList.split("[,\\[\\]]"))
                        .filter(
                                a -> !a.trim().isEmpty()
                        ).toArray()[0]
        );
        assertEquals(2L, itemId);
        String urlItemCompletion = baseURL + "/item/" + itemId + "/completion";

        Item item = this.restTemplate.getForObject(urlItem + "/" + itemId, Item.class);
        assertNotNull(item);
        assertEquals(2L, item.getId());

        int completion = this.restTemplate.getForObject(urlItemCompletion, int.class);
        assertEquals(0, completion);

        completion = 100;
        completion = this.restTemplate.postForObject(urlItemCompletion, completion, int.class);
        assertEquals(100, completion);
        completion = this.restTemplate.getForObject(urlItemCompletion, int.class);
        assertEquals(100, completion);
    }

    @Test
    void testDeletingTask() {
        String urlItem = baseURL + "/item";
        String urlIds = baseURL + "/todoIds";
        String url = baseURL + "/todos";

        String idList = this.restTemplate.getForObject(urlIds, String.class);
        assertEquals("[1]", idList);//from previous test
        Long itemId = Long.parseLong(
                (String) Arrays.stream(idList.split("[,\\[\\]]"))
                        .filter(
                                a -> !a.trim().isEmpty()
                        ).toArray()[0]
        );
        assertEquals(1L, itemId);

        Item item = this.restTemplate.getForObject(urlItem + "/" + itemId, Item.class);
        assertNotNull(item);
        assertEquals(1L, item.getId());
        assertEquals(taskHeader, item.getHeader());
        this.restTemplate.delete(urlItem + "/" + itemId);
        item = this.restTemplate.getForObject(urlItem + "/" + itemId, Item.class);
        assertNull(item);

    }

    @Test
    void testSettingPriority(){
        String urlItem = baseURL + "/item";
        String urlIds = baseURL + "/todoIds";
        String url = baseURL + "/todos";
        String urlPriority = baseURL + "/priority";

        //setup
        String idList = this.restTemplate.getForObject(urlIds, String.class);
        assertEquals("[2]", idList);//from previous test
        Long itemId = Long.parseLong(
                (String) Arrays.stream(idList.split("[,\\[\\]]"))
                        .filter(
                                a -> !a.trim().isEmpty()
                        ).toArray()[0]
        );
        assertEquals(2L, itemId);

        //get priority
        String urlItemPriority = baseURL + "/item/" + itemId + "/priority";
        Item.Priority priority = this.restTemplate.getForObject(urlItemPriority, Item.Priority.class);
        assertEquals(Item.Priority.LOW, priority);

        //set priority
        this.restTemplate.postForObject(urlItemPriority, Item.Priority.HIGH, Item.Priority.class);
        priority = this.restTemplate.getForObject(urlItemPriority, Item.Priority.class);
        assertEquals(Item.Priority.HIGH, priority);



    }

}
