package dev.fespinosa.cloudstorage.services;

import dev.fespinosa.cloudstorage.mappers.UserMapper;
import dev.fespinosa.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private UserMapper userMapper;
    private HashService hashService;

    @Autowired
    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public int createUser(User user) {
        String encodedSalt = hashService.generateEncodedSalt();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(hashedPassword);
        newUser.setSalt(encodedSalt);
        return userMapper.insert(newUser);
    }

    public Optional<User> findUserByUsername(String username) {
        return userMapper.getUser(username);
    }

}
