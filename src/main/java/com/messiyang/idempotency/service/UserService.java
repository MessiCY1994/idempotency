package com.messiyang.idempotency.service;


import com.messiyang.idempotency.common.ServerResponse;
import com.messiyang.idempotency.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getAll();

    User getOne(Long id);

    void add(User user);

    void update(User user);

    void delete(Long id);

    User getByUsernameAndPassword(String username, String password);

    ServerResponse login(String username, String password);

}
