package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.UserDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.user.User;


public interface UserService {
    User getCurrentUser();
    User getUser(Long id);
    User getById(Long id);
    void save(User user);
}
