package com.messiyang.idempotency.mapper;


import com.messiyang.idempotency.pojo.LoginLog;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogMapper {

    void insert(LoginLog loginLog);

    LoginLog selectByMsgId(String msgId);

}
