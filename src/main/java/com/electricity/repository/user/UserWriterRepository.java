package com.electricity.repository.user;

import com.electricity.model.user.User;

public interface UserWriterRepository {

    void insert(User user);

    void update(User user);
}