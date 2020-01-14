package com.messiyang.idempotency.mapper;



import com.messiyang.idempotency.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface UserMapper {

    List<User> selectAll();

    User selectOne(Integer id);

    void insert(User user);

    void update(User user);

    void delete(Integer id);

    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
