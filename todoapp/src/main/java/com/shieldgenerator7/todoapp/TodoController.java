package com.shieldgenerator7.todoapp;

import com.shieldgenerator7.todoapp.data.Item;
import com.shieldgenerator7.todoapp.data.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidKeyException;
import java.util.List;

@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class TodoController {

    private static int MAX_HEADER_LENGTH = 100;

    @Autowired
    ItemRepository repository;

    /**
     * Returns a list of all todos
     * @return A list of Item objects
     */
    @GetMapping("/todos")
    public List<Item> getTodos() {
        return repository.findAll();
    }

    /**
     * Adds an Item to the list
     * @param item The Item to add
     * @return A ResponseEntity that contains the added Item, if it was added
     * @throws IllegalArgumentException if the Item was null or had an invalid header
     * @throws DuplicateKeyException if the list already has an Item with the same header
     */
    @PostMapping("/todos")
    public ResponseEntity<?> addTodo(@RequestBody Item item) {
        printItem(item);
        try {
            validateHeader(item);
        } catch (IllegalArgumentException | DuplicateKeyException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        repository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    /**
     * Returns a list of items that have a header that contains the given query, not case-sensitive
     * @param query The text to search for
     * @return A list of Item
     */
    @GetMapping("/todos/search")
    public List<Item> searchTodoIds(@RequestParam(value = "title") String query) {
        System.out.println("======== " + query + " ===============");
        return repository.findByHeaderContainingIgnoreCase(query);
    }

    /**
     * Returns the Item with the given id
     * @param itemId The item that has the given id
     * @return The Item with the given id, null if not found
     */
    @GetMapping("/todos/item/{itemId}")
    public Item getItem(@PathVariable Long itemId) {
        Item item = getItemById(itemId);
        printItem(item);
        return item;
    }

    /**
     * Deletes the Item with the given id
     * @param itemId The id of the Item to delete
     */
    @DeleteMapping("/todos/item/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        if (!this.repository.existsById(itemId)){
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns the completion status of the item with the given id
     * @param itemId The id of the Item
     * @return The completion status of the Item
     */
    @GetMapping("/todos/item/{itemId}/completion")
    public int getItemCompletion(@PathVariable int itemId) {
        Item item = getItemById((long)itemId);
        return item.getCompletionStatus();
    }

    /**
     * Sets the completion status of the item with the given id to the given value
     * @param itemId The id of the Item
     * @param completionStatus The completions status to set it to
     * @return The new current completion status of the Item, between 0 and 100, inclusive
     */
    @PutMapping("/todos/item/{itemId}/completion")
    public ResponseEntity<?> updateItemCompletion(@PathVariable Long itemId, @RequestBody int completionStatus) {
        Item item = getItemById(itemId);
        int completed = item.setCompletionStatus(completionStatus);
        repository.save(item);
        return ResponseEntity.ok(item);
    }

    /**
     * Returns the priority of the Item with the given id: LOW, MEDIUM, or HIGH
     * @param itemId The id of the Item
     * @return The priority of the Item: LOW, MEDIUM, or HIGH
     */
    @GetMapping("/todos/item/{itemId}/priority")
    public Priority getItemPriority(@PathVariable Long itemId) {
        Item item = getItemById(itemId);
        return item.getPriority();
    }

    /**
     * Sets the priority of the Item with the given id to the given priority
     * @param itemId The id of the Item
     * @param priority The priority to set it to
     */
    @PutMapping("/todos/item/{itemId}/priority")
    public ResponseEntity<?> updateItemPriority(@PathVariable Long itemId, @RequestBody Priority priority) {
        Item item = getItemById(itemId);
        item.setPriority(priority);
        repository.save(item);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/")
    public String root() {
        return "<h1>test list</h1>";
    }

    /**
     * Validates the header of the given item.
     * A header is valid if it:
     * * Has between 1 and 100 characters, inclusive
     * * Is not a duplicate of another existing item's header
     * @param item The item that has the header to validate
     * @throws IllegalArgumentException if the header is invalid
     * @throws DuplicateKeyException if the header is a duplicate
     */
    private void validateHeader(Item item) throws IllegalArgumentException, DuplicateKeyException{
        if (item == null){
            throw new IllegalArgumentException("Item must not be null!");
        }
        validateHeader(item.getHeader());
    }
    /**
     * Validates the given header.
     * A header is valid if it:
     * * Has between 1 and 100 characters, inclusive
     * * Is not a duplicate of another existing item's header
     * @param itemHeader The header to validate
     * @throws IllegalArgumentException if the header is invalid
     * @throws DuplicateKeyException if the header is a duplicate
     */
    private void validateHeader(String itemHeader) throws IllegalArgumentException, DuplicateKeyException {
        //not empty
        if (itemHeader == null || itemHeader.trim().isEmpty()) {
            throw new IllegalArgumentException("Item header must not be empty!");
        }

        //header length
        if (itemHeader.length() > MAX_HEADER_LENGTH) {
            throw new IllegalArgumentException("Item header must be 100 characters or less!");
        }

        //no duplicates
        if (repository.existsByHeaderIgnoreCase(itemHeader)) {
            throw new DuplicateKeyException("There's already a task with header \"" + itemHeader + "\"!");
        }

        //no errors, return
    }

    /**
     * Returns the item with the given id
     * @param id The id of the item
     * @return The item with the id
     * @throws ResponseStatusException with NOT_FOUND if there is not item with the given id
     */
    public Item getItemById(Long id) {
        try {
        return repository.findById(id).orElseThrow(InvalidKeyException::new);
        }
        catch(InvalidKeyException ike){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with id: "+id);
        }
    }

    /**
     * Prints the given item to the console for testing purposes
     * @param item The item to print
     */
    private void printItem(Item item){
        if (item == null){
            System.out.printf("todo %s", item);
            return;
        }
        System.out.printf("todo %s, %s, %s%n", item.getHeader(), item.getCompletionStatus(), item.getPriority());
    }

}
