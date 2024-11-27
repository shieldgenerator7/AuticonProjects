package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import com.shieldgenerator7.todoapp.data.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Item item = new Item(todo);
        todoList.add(item);
        repository.save(todoList);
        return todo;
    }

    @GetMapping("/todoIds")
    public List<Long> getTodoIds() {
        TodoList todoList = repository.findAll().get(0);
        return todoList.getTodos().stream()
                .map(Item::getId).toList();
    }

    @GetMapping("/item/{itemId}")
    public Item getItem(@PathVariable Long itemId) {
        TodoList todoList = repository.findAll().get(0);
        return todoList.getItemById(itemId);
    }

    @DeleteMapping("item/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        TodoList todoList = repository.findAll().get(0);
        todoList.removeById(itemId);
        repository.save(todoList);
    }


    @GetMapping("/item/{itemId}/completion")
    public int getItemCompletion(@PathVariable Long itemId) {
        TodoList todoList = repository.findAll().get(0);
        Item item = todoList.getItemById(itemId);
        return item.getCompletionStatus();
    }

    @PostMapping("/item/{itemId}/completion")
    public int updateItemCompletion(@PathVariable Long itemId, @RequestBody int completionStatus) {
        TodoList todoList = repository.findAll().get(0);
        Item item = todoList.getItemById(itemId);
        int completed = item.setCompletionStatus(completionStatus);
        repository.save(todoList);
        return completed;
    }

    @GetMapping("/")
    public String root() {
        return "<h1>test list</h1>";
    }

}
