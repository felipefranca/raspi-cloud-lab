package com.docker.cloud_learn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    @Autowired
    private MessageRepository repository;

    @GetMapping
    public List<Message> hello() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Message> save(@RequestBody Message message) {
        var result = repository.save(message);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public String sayHello() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Hello! " + LocalDateTime.now().format(formatter);
    }
}
