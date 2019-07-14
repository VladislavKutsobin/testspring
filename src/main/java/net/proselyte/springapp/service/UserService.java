package net.proselyte.springapp.service;

import net.proselyte.springapp.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
