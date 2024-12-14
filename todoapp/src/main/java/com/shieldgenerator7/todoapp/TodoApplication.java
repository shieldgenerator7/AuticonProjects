package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.shieldgenerator7.todoapp");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Item item1 = new Item("buy eggs");
        em.persist(item1);
        Item item2 = new Item("buy groceries");
        em.persist(item2);


        em.getTransaction().commit();

        int key = 1;
        Item item = (Item)em.getReference(Item.class, key);
        if (item != null){
            System.out.println("item "+key+": "+ item.getHeader());
        }
        else{
            System.out.println("item "+key+": "+item);
        }

        emf.close();

    }

}
