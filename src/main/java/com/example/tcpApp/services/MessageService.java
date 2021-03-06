package com.example.tcpApp.services;

import com.example.tcpApp.models.Message;
import com.example.tcpApp.repositories.MessageRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRespository messageRespository;

    public Message create(Message message){
        message.setTimestamp(new Date());
        return messageRespository.save(message);
    }
    public Iterable<Message> findAll(){
        return messageRespository.findAll();
    }

    public List<Message> findAllByChannel(String channel, Pageable pageable){
        return messageRespository.findAllByChannel(channel, pageable);
    }

    public List<Message> findBySender(String sender, Pageable pageable){
        return messageRespository.findBySender(sender, pageable);
    }

    public Message findById(Long id){
        return messageRespository.getOne(id);
    }

    public Boolean delete(Long id){
        messageRespository.deleteById(id);
        return true;
    }

    public Boolean deleteAll(){
        messageRespository.deleteAll();
        return true;
    }

    public Message updateMessage(Long id, String content){
        Message original = messageRespository.getOne(id);
        original.setMessageContent(content);
        return messageRespository.save(original);
    }
}