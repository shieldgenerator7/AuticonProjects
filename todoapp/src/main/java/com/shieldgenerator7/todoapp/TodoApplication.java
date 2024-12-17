package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableTransactionManagement
//@EnableJpaRepositories("com.shieldgenerator7.todoapp")
public class TodoApplication {

//    public static EntityManagerFactory emf;
//    public static EntityManager em;

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);

//        emf = Persistence.createEntityManagerFactory("com.shieldgenerator7.todoapp");
//        em = emf.createEntityManager();

//        em.getTransaction().begin();
//
//        Item item1 = new Item("buy eggs");
//        em.persist(item1);
//        Item item2 = new Item("buy groceries");
//        em.persist(item2);
//
//
//        em.getTransaction().commit();
//
//        int key = 1;
//        Item item = (Item)em.getReference(Item.class, key);
//        if (item != null){
//            System.out.println("item "+key+": "+ item.getHeader());
//        }
//        else{
//            System.out.println("item "+key+": "+item);
//        }
//
//        emf.close();

    }

}
