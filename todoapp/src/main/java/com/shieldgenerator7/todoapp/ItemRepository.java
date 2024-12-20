package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}