package com.example.tcpApp.controllers;

import com.example.tcpApp.models.Message;
import com.example.tcpApp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Message> sendMessage(@RequestBody Message message){
        return new ResponseEntity<>(messageService.create(message), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Message>> findAll(){
        return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findAll/{channel}")
    public ResponseEntity<List<Message>> findAllByChannel(@PathVariable String channel, Pageable pageable) {
        return new ResponseEntity<>(messageService.findAllByChannel(channel, pageable), HttpStatus.OK);
    }

    @GetMapping("/findBySender/{username}")
    public  ResponseEntity<List<Message>> findBySender(@PathVariable String username, Pageable pageable){
        return new ResponseEntity<>(messageService.findBySender(username, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return new ResponseEntity<>(messageService.delete(id), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAllMessages(){
        return new ResponseEntity<>(messageService.deleteAll(), HttpStatus.NOT_FOUND);
    }

    @MessageMapping("/messages")
    @SendTo("/topic/reply")
    public Message send(Message message, @PathVariable String channel) {
        return sendMessage(message).getBody();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestParam String content){
        return new ResponseEntity<>(messageService.updateMessage(id, content), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id){
        return new ResponseEntity<>(messageService.findById(id), HttpStatus.OK);
    }
}
