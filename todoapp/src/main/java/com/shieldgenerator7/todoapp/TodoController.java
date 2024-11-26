package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import com.shieldgenerator7.todoapp.data.TodoList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

public class TodoController {

    private TodoList todoList = new TodoList();

    @GetMapping("/todos")
    public List<String> getTodos() {
        return todoList.getTodos().stream()
                .map(Item::getHeader).toList();
    }

    @PostMapping("/todos")
    public String addTodo(@RequestBody String todo) {
        Item item = new Item(todo);
        todoList.add(item);
        return todo;
    }

}
