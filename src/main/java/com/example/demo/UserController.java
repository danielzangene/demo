package com.example.demo;


import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all users list.
     *
     * @return the list.
     */

    @GetMapping("/users")
    public List<User> gettAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets user by id.
     *
     * @param userId the user id.
     * @return the users by id
     * @throws ResourceNotFoundException the resource not found exception.
     */

    @GetMapping("/users{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on ::" + userId));
        return ResponseEntity.ok().body(user);

    }

    /**
     * create user user.
     *
     * @param user the user.
     * @return the user
     */

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * Update user response entity.
     *
     * @param userId      the user id.
     * @param userDetails the user details.
     * @return the response entity.
     * @throws ResourceNotFoundException the resource not found exception.
     */

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                           @Valid @RequestBody User userDetails)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("" +
                "User not found on ::" + userId));
        user.setEmail(userDetails.getEmail());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        final User updateUser = userRepository.save(user);
        return ResponseEntity.ok(updateUser);
    }

    /**
     * Deleet user map.
     *
     * @param userId the user id.
     * @return the map.
     * @throw Exception the exception.
     */

    @DeleteMapping("/user/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                "User not found on ::" + userId));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/test")
    public String  test(){
        User user = new User();
        user.setFirstName("tina");
        user.setLastName("sade");
        user.setEmail("tina@gmail.com");
        User savedUser = userRepository.save(user);
        return savedUser.toString();
    }

}
