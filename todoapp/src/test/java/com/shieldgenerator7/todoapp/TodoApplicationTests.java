package com.shieldgenerator7.todoapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shieldgenerator7.todoapp.data.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Arrays;
import java.util.List;

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

    List<Long> idList;

    @BeforeEach
    void initTests(){
        baseURL = "http://localhost:" + port + "/todos";
    }

    @AfterEach
    void cleanupTests() throws JsonProcessingException {
        String urlItem = baseURL + "/item";
        String urlIds = baseURL + "/ids";
        String idListString = this.restTemplate.getForObject(urlIds, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Long> idList = mapper.readValue(idListString, new TypeReference<List<Long>>() {});
        idList.forEach(id->{
            this.restTemplate.delete(urlItem + "/" + id);
        });
    }

    void _addTestItems(){
        String url = baseURL;
        String urlIds = baseURL + "/ids";

        //add items
        this.restTemplate.postForObject(url, "take a shower", String.class);
        this.restTemplate.postForObject(url, "buy eggs", String.class);
        this.restTemplate.postForObject(url, "buy groceries", String.class);

        //update item id list
        try {
            idList = null;
            String idListString = this.restTemplate.getForObject(urlIds, String.class);
            ObjectMapper mapper = new ObjectMapper();
            idList = mapper.readValue(idListString, new TypeReference<List<Long>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testController() {
        String url = baseURL;

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
        String url = baseURL;
        String urlIds = baseURL + "/ids";
        String urlItem = baseURL + "/item";

        _addTestItems();

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
        String urlIds = baseURL + "/ids";

        _addTestItems();
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
        String urlIds = baseURL + "/ids";

        //setup
        _addTestItems();
        String idList = this.restTemplate.getForObject(urlIds, String.class);
        assertEquals("[2,3,4]", idList);//from previous test
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

    @Test
    void testSearch(){
        String urlIds = baseURL + "/ids";
        String urlSearch = baseURL + "/search";

        //setup
        _addTestItems();

        //search
        String query;
        ObjectMapper mapper = new ObjectMapper();
        List result;

        //found search
        query = "buy";
        result = this.restTemplate.getForObject(urlSearch+"?title="+query, List.class);
        assertNotNull(result);
        List<Item> searchItems = result.stream().map(item-> mapper.convertValue(item, Item.class)).toList();
        assertEquals(2, searchItems.size());
        assertEquals("buy eggs", searchItems.get(0).getHeader());
        assertEquals("buy groceries", searchItems.get(1).getHeader());

        //not found search
        query = "find";
        result = this.restTemplate.getForObject(urlSearch+"?title="+query, List.class);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

}
