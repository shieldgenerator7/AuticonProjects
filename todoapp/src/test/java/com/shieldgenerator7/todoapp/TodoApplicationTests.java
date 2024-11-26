package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import com.shieldgenerator7.todoapp.data.TodoList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TodoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testAddItem(){
		final String taskHeader = "buy groceries";
		TodoList list = new TodoList();
		assertEquals(0, list.getCount());
		Item item = new Item(taskHeader);
		assertEquals(taskHeader, item.getHeader());

		list.add(item);
		assertEquals(1, list.getCount());
		assertEquals(taskHeader, list.getItem(0).getHeader());
	}

}
