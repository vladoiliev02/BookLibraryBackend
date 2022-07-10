package com.fmi.wdj.booklibrary.service.user;

import com.fmi.wdj.booklibrary.model.user.User;
import com.fmi.wdj.booklibrary.model.user.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    User updateInfo(String username, UserInfo newInfo);

    void removeUser(String username);

    List<User> getAllUsers();

    Optional<User> getUserByUsername(String username);

    boolean exists(String username);

    UserInfo getUserInfo(String username);

    boolean isAdmin(String username);
}
