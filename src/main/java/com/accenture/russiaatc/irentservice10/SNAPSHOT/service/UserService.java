package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;


import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.UserDto;

// получ данных по текущ пользователю

public interface UserService {
    UserDto getUser(Long id);
}
