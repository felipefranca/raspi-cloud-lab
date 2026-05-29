package com.docker.raspi_cloud_lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private MessageRepository repository;

    @GetMapping
    public List<Message> hello() {
        log.info("Todas as mensagens recuperadas");
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Message> save(@RequestBody Message message) {
        var result = repository.save(message);
        log.info("Mensagem criada");
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public String sayHello() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Hello mother fucker! " + LocalDateTime.now().format(formatter);
    }
}
