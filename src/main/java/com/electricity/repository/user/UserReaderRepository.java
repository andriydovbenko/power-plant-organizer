package com.electricity.repository.user;

import com.electricity.model.user.User;

public interface UserReaderRepository {

    User selectByLogin(String login);
}