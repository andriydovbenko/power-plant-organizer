package com.electricity.repository.user;

import com.electricity.model.user.User;

import java.util.List;

public interface UserReaderRepository {

    User selectByLogin(String login);

    List<User> selectAll();
}