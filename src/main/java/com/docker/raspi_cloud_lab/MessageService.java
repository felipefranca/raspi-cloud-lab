package com.docker.raspi_cloud_lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageRepository repository;

    public List<Message> findAll(){
        log.info("[Service] Recuperando todas as mensagens");
        return repository.findAll();
    }

    public Message save(Message message){
        log.info("[Service] - Mensagem criada");
        return repository.save(message);
    }
}
