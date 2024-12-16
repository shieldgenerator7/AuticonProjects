package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.TodoList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class TodoListRepositoryManager {

    private EntityManagerFactory emf;
    private TodoList todoList;

    public TodoListRepositoryManager() {
        emf = Persistence.createEntityManagerFactory("com.shieldgenerator7.todoapp");
    }

    public void persist(Object entity){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(entity);

        em.getTransaction().commit();
        em.close();
    }

    public TodoList getTodoList() {
        if (todoList == null) {
        //set up
        EntityManager em = emf.createEntityManager();
        //get id
        Query queryFirstTodoList = em.createNativeQuery("SELECT id FROM todoList ORDER BY id ASC LIMIT 1;");
        List idList = queryFirstTodoList.getResultList();
        //if no id found,
        if (idList == null || idList.isEmpty()) {
            //insert new todo list
            em.getTransaction().begin();
            TodoList todoList = new TodoList();
            em.persist(todoList);
            em.getTransaction().commit();
            idList = queryFirstTodoList.getResultList();
        }
        //get todo list
        Long key = (Long) idList.get(0);
        todoList = (TodoList) em.getReference(TodoList.class, key);
        //clean up
        em.close();
        }
        //return todo list
        return todoList;
    }
}
