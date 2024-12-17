package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

}
