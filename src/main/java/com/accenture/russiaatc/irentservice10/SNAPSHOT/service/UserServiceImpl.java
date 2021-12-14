package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;


import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.user.User;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.UserDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// сделать названия методов единообразными, схожими по названиям

@Service
public class UserServiceImpl implements UserService{
    final private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return toUserDto(user);
    }

    public UserDto toUserDto (User user){
        UserDto userDto = new UserDto();
        userDto.setBalance(user.getBalance());
        userDto.setLogin(user.getLogin());
        userDto.setId(user.getId());
        return userDto;
    }


}
