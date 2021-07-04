package com.shift.chat;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("database")
public class MessagesController {

    private int counter = 0;

    private List<Map<String, String>> dataBase = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", String.valueOf(counter));
            put("firstName", "Nikita");
            put("lastName", "Mameev");
        }});
    }};

    @GetMapping("/users")
    public List<Map<String, String>> list() {
        return dataBase;
    }

    @GetMapping("/user/{id}")
    public Map<String, String> getUserById(@PathVariable String id) {
        return dataBase.stream()
                .filter(messages -> messages.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("/user")
    public Map<String, String> createUser(@RequestBody Map<String, String> user) {
        user.put("id", String.valueOf(counter++));
        dataBase.add(user);
        return user;
    }

    @PostMapping("/user/{id}")
    public Map<String, String> updateUser(@PathVariable String id, @RequestBody Map<String, String> user) {
        Map<String, String> userFromDataBase = getUserById(id);
        userFromDataBase.putAll(user);
        return userFromDataBase;
    }

    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable String id) {
        Map<String, String> user = getUserById(id);
        dataBase.remove(user);
    }
}
