package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import com.shieldgenerator7.todoapp.data.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return todo;
    }

    @GetMapping("/")
    public String root(){
        return "<h1>test list</h1>";
    }

}
