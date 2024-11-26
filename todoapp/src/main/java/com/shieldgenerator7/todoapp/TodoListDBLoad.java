package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.TodoList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoListDBLoad {
    @Bean
    CommandLineRunner initDatabase(TodoListRepository repository){
        return args->{
            repository.save(new TodoList());
        };
    }

}
