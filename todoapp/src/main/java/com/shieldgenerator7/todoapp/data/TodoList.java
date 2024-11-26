package com.shieldgenerator7.todoapp.data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TodoList {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> todos = new ArrayList<>();

    public void add(Item item) {
        if (!todos.contains(item)) {
            todos.add(item);
        }
    }

    public List<Item> getTodos() {
        return todos.subList(0, todos.size());
    }

    public int getCount() {
        return todos.size();
    }

    public Item getItem(int index) {
        return todos.get(index);
    }

}
