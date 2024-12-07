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
        todoList.add(todo);
        repository.save(todoList);
        return todo;
    }

    @GetMapping("/todoIds")
    public List<Long> getTodoIds() {
        TodoList todoList = repository.findAll().get(0);
        return todoList.getTodos().stream()
                .map(Item::getId).toList();
    }

    @GetMapping("/search")
    public List<Item> searchTodoIds(@RequestParam(value="title") String query) {
        System.out.println("======== "+query+" ===============");
        TodoList todoList = repository.findAll().get(0);
        return todoList.searchItems(query);
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

    @GetMapping("/item/{itemId}/priority")
    public Item.Priority getItemPriority(@PathVariable Long itemId) {
        TodoList todoList = repository.findAll().get(0);
        Item item = todoList.getItemById(itemId);
        return item.getPriority();
    }

    @PostMapping("/item/{itemId}/priority")
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
