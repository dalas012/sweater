package com.example.sweater.rest;

import com.example.sweater.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rest/messages")
public class MessageRestController {

    private int counter = 4;

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message"); }});
    }};

    @GetMapping
    public List<Map<String, String>> getAll() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> get(@PathVariable String id) {
        return getMessageById(id);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(
            @RequestBody Map<String, String> message,
            @PathVariable String id
    ) {
        Map<String, String> messageFromDb = getMessageById(id);
        messageFromDb.putAll(message);
        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> messageFromDb = getMessageById(id);
        messages.remove(messageFromDb);
    }

    private Map<String, String> getMessageById(String id) {
        for (Map<String, String> m : messages) {
            if (m.get("id").equals(id)) {
                return m;
            }
        }
        throw new NotFoundException("Resource was not found");
    }

}
