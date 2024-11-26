package com.shieldgenerator7.todoapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

public class TodoController {

    private List<String> todos = new ArrayList<>();
    @GetMapping("/todos")
    public List<String> getTodos() {
        return todos;
    }
    @PostMapping("/todos")
    public String addTodo(@RequestBody String todo) {
        todos.add(todo);
        return todo;
    }

}
