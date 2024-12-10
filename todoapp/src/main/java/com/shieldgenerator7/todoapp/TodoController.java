package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import com.shieldgenerator7.todoapp.data.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    TodoListRepository repository;

    @GetMapping("/todos")
    public List<String> getTodos() {
        TodoList todoList = repository.findAll().get(0);
        return todoList.getTodos().stream()
                .map(Item::getHeader).toList();
    }

    @PostMapping("/todos")
    public String addTodo(@RequestBody String todo) {
        TodoList todoList = repository.findAll().get(0);
        try {
            todoList.add(todo);
            repository.save(todoList);
            return todo;
        } catch (IllegalArgumentException iae) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, iae.getMessage());
        } catch (DuplicateKeyException dke) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, dke.getMessage());
        }
    }

    @GetMapping("/todos/ids")
    public List<Long> getTodoIds() {
        TodoList todoList = repository.findAll().get(0);
        return todoList.getTodos().stream()
                .map(Item::getId).toList();
    }

    @GetMapping("/todos/search")
    public List<Item> searchTodoIds(@RequestParam(value = "title") String query) {
        System.out.println("======== " + query + " ===============");
        TodoList todoList = repository.findAll().get(0);
        return todoList.searchItems(query);
    }

    @GetMapping("/todos/item/{itemId}")
    public Item getItem(@PathVariable Long itemId) {
        TodoList todoList = repository.findAll().get(0);
        return todoList.getItemById(itemId);
    }

    @DeleteMapping("todos/item/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        TodoList todoList = repository.findAll().get(0);
        todoList.removeById(itemId);
        repository.save(todoList);
    }


    @GetMapping("/todos/item/{itemId}/completion")
    public int getItemCompletion(@PathVariable Long itemId) {
        TodoList todoList = repository.findAll().get(0);
        Item item = todoList.getItemById(itemId);
        return item.getCompletionStatus();
    }

    @PostMapping("/todos/item/{itemId}/completion")
    public int updateItemCompletion(@PathVariable Long itemId, @RequestBody int completionStatus) {
        TodoList todoList = repository.findAll().get(0);
        Item item = todoList.getItemById(itemId);
        int completed = item.setCompletionStatus(completionStatus);
        repository.save(todoList);
        return completed;
    }

    @GetMapping("/todos/item/{itemId}/priority")
    public Item.Priority getItemPriority(@PathVariable Long itemId) {
        TodoList todoList = repository.findAll().get(0);
        Item item = todoList.getItemById(itemId);
        return item.getPriority();
    }

    @PostMapping("/todos/item/{itemId}/priority")
    public void updateItemPriority(@PathVariable Long itemId, @RequestBody Item.Priority priority) {
        TodoList todoList = repository.findAll().get(0);
        Item item = todoList.getItemById(itemId);
        item.setPriority(priority);
        repository.save(todoList);
    }

    @GetMapping("/")
    public String root() {
        return "<h1>test list</h1>";
    }

}
