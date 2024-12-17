package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.TodoList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    //2024-12-10: copied from https://stackoverflow.com/a/35929057/2336212
//    @Autowired
//    private EntityManager entityManager;

//    @Transactional
//    public default void persist() {
////        _checkEntityManager();
//        EntityManager entityManager = TodoApplication.em;
//        entityManager.getTransaction().begin();
////                TodoApplication.emf
////                .createEntityManager();
//        entityManager.persist(this);
//        entityManager.getTransaction().commit();
////        entityManager.close();
////        this.entityManager.persist(this);
//    }

//    @Transactional
//    public TodoList merge(TodoList todolist) {
//        _checkEntityManager();
//        TodoList merged = this.entityManager.merge(todolist);
//        this.entityManager.flush();
//        return merged;
//    }
//
//    private void _checkEntityManager(){
//        if (this.entityManager == null) {
//            this.entityManager = Persistence
//                    .createEntityManagerFactory("com.shieldgenerator7.todoapp")
//                    .createEntityManager();
//        }
//    }

}
