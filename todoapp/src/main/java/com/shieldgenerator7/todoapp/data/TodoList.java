package com.shieldgenerator7.todoapp.data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

@Entity
public class TodoList {

    private static int MAX_HEADER_LENGTH = 100;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> todos = new ArrayList<>();

    public void add(String itemHeader){
        //validation

        //not empty
        if (itemHeader == null || itemHeader.trim().isEmpty()){
            return;
        }

        //header length
        if (itemHeader.length() > MAX_HEADER_LENGTH){
            return;
        }

        //no duplicates
        if (todos.stream().anyMatch(todo-> todo.getHeader().equals(itemHeader))){
            return;
        }

        //add
        Item item = new Item(itemHeader);
        todos.add(item);
    }

    public void add(Item item) {

        //validate: not empty
        if (item == null){
            return;
        }

        //validate: not duplicate
        if (todos.contains(item)) {
            return;
        }

            todos.add(item);
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

    public Item getItemById(Long id) {
        if (id == null) {
            return null;
        }
        try {
            return todos.stream().filter(i -> id.equals(i.getId())).findFirst().get();
        } catch (NoSuchElementException nsee) {
            return null;
        }
    }

    public void removeById(Long itemId) {
        Item item = getItemById(itemId);
        if (item != null) {
            todos.remove(item);
        }
    }

    public List<Item> searchItems(String query){
        return searchItems(item->item.getHeader().contains(query));
    }
    public List<Item> searchItems(Predicate<Item> searchFunc){
        return todos.stream().filter(searchFunc).toList();
    }
}
