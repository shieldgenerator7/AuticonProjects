package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    public boolean existsByHeaderIgnoreCase(String header);
    public List<Item> findByHeaderContainingIgnoreCase(String header);
}