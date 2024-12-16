package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.TodoList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class TodoListRepositoryManager {

    EntityManagerFactory emf;

    public TodoListRepositoryManager() {
        emf = Persistence.createEntityManagerFactory("com.shieldgenerator7.todoapp");
    }

    public TodoList getTodoList() {
        EntityManager em = emf.createEntityManager();
        Query queryFirstTodoList = em.createNativeQuery("SELECT id FROM todoList ORDER BY id ASC LIMIT 1;");
        System.out.println("query length: " + queryFirstTodoList.getResultList().size());
        List idList = queryFirstTodoList.getResultList();
        if (idList == null || idList.isEmpty()) {
            em.getTransaction().begin();
            TodoList todoList = new TodoList();
            em.persist(todoList);
            em.getTransaction().commit();
            idList = queryFirstTodoList.getResultList();
        }
        Long key = (Long) queryFirstTodoList.getResultList().get(0);
        System.out.println("key: " + key);
        TodoList todoList = (TodoList) em.getReference(TodoList.class, key);
        if (todoList != null) {
            System.out.println("todolist " + todoList.getId() + ": ");

        }
        em.close();
        return null;
    }
}
