package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.TodoList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    @Transactional
    public default void persist() {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("com.shieldgenerator7.todoapp")
                .createEntityManager();
        entityManager.persist(this);
    }

}
