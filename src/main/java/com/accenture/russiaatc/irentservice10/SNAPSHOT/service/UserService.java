package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;


import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.UserDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.user.User;

// получ данных по текущ пользователю

public interface UserService {
    UserDto getUser(Long id);
    User getById(Long id);
}
