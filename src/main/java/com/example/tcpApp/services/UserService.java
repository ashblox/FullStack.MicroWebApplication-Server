package com.example.tcpApp.services;

import com.example.tcpApp.models.Channel;
import com.example.tcpApp.models.User;
import com.example.tcpApp.repositories.ChannelRepository;
import com.example.tcpApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ChannelRepository channelRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User create(User user) throws Exception {
        if(userRepository.findByUsername(user.getUsername()) == null) {
            return userRepository.save(user);
        }
        throw new Exception("Username already exists!");
    }

    public Boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    public User connect(Long id){
        User original = userRepository.getOne(id);
        original.setConnected(true);
        return userRepository.save(original);
    }

    public User login(String username){
       User original = userRepository.findByUsername(username);
       original.setConnected(true);
        return userRepository.save(original);
    }

    public User disconnect(Long id){
        User original = userRepository.getOne(id);
        original.setConnected(false);
        return userRepository.save(original);
    }

    public User logout(String username){
        User original = userRepository.findByUsername(username);
        original.setConnected(false);
        return userRepository.save(original);
    }

    public User joinChannel(Long userId, Long channelId){
        User original = userRepository.getOne(userId);
        Channel channel = channelRepository.getOne(channelId);
        original.getChannels().add(channel);
        channelService.addUser(original, channelId);
        return userRepository.save(original);
    }
}
