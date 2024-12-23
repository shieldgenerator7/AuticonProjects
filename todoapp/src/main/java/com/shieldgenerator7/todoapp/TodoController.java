package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import com.shieldgenerator7.todoapp.data.Priority;
import com.shieldgenerator7.todoapp.data.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class TodoController {

    private static int MAX_HEADER_LENGTH = 100;

    @Autowired
    ItemRepository repository;

    @GetMapping("/todos")
    public List<Item> getTodos() {
        return repository.findAll();
    }

    @PostMapping("/todos")
    public ResponseEntity<Item> addTodo(@RequestBody Item todo) {
        printItem(todo);
        try {
            validateHeader(todo);
        } catch (IllegalArgumentException iae) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, iae.getMessage());
        } catch (DuplicateKeyException dke) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, dke.getMessage());
        }

        repository.save(todo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @GetMapping("/todos/search")
    public List<Item> searchTodoIds(@RequestParam(value = "title") String query) {
        System.out.println("======== " + query + " ===============");
        return repository.findByHeaderContainingIgnoreCase(query);
    }

    @GetMapping("/todos/item/{itemId}")
    public Item getItem(@PathVariable Long itemId) throws InvalidKeyException {
        Item item = getItemById(itemId);
        printItem(item);
        return item;
    }

    @DeleteMapping("/todos/item/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        repository.deleteById(itemId);
    }


    @GetMapping("/todos/item/{itemId}/completion")
    public int getItemCompletion(@PathVariable int itemId) throws InvalidKeyException {
        Item item = getItemById((long)itemId);
        return item.getCompletionStatus();
    }

    @PutMapping("/todos/item/{itemId}/completion")
    public int updateItemCompletion(@PathVariable Long itemId, @RequestBody int completionStatus) throws InvalidKeyException {
        Item item = getItemById(itemId);
        int completed = item.setCompletionStatus(completionStatus);
        repository.save(item);
        return completed;
    }

    @GetMapping("/todos/item/{itemId}/priority")
    public Priority getItemPriority(@PathVariable Long itemId) throws InvalidKeyException {
        Item item = getItemById(itemId);
        return item.getPriority();
    }

    @PutMapping("/todos/item/{itemId}/priority")
    public void updateItemPriority(@PathVariable Long itemId, @RequestBody Priority priority) throws InvalidKeyException {
        Item item = getItemById(itemId);
        item.setPriority(priority);
        repository.save(item);
    }

    @GetMapping("/")
    public String root() {
        return "<h1>test list</h1>";
    }

    private void validateHeader(Item item){
        if (item == null){
            throw new IllegalArgumentException("Item must not be null!");
        }
        validateHeader(item.getHeader());
    }
    private void validateHeader(String itemHeader) {
        //not empty
        if (itemHeader == null || itemHeader.trim().isEmpty()) {
            throw new IllegalArgumentException("Item header must not be empty!");
        }

        //header length
        if (itemHeader.length() > MAX_HEADER_LENGTH) {
            throw new IllegalArgumentException("Item header must be 100 characters or less!");
        }

        //no duplicates
        if (repository.existsByHeaderIgnoreCase(itemHeader)) {
            throw new DuplicateKeyException("There's already a task with header \"" + itemHeader + "\"!");
        }

        //no errors, return
    }

    public Item getItemById(Long id) throws InvalidKeyException {
        return repository.findById(id).orElseThrow(InvalidKeyException::new);
    }
    
    private void printItem(Item todo){
        System.out.println(String.format("todo %s, %s, %s", todo.getHeader(), todo.getCompletionStatus(), todo.getPriority()));
    }

}
