package com.messiyang.idempotency.service;


import com.messiyang.idempotency.pojo.LoginLog;

public interface LoginLogService {

    void insert(LoginLog loginLog);

    LoginLog selectByMsgId(String msgId);

}
