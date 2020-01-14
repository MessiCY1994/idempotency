package com.messiyang.idempotency.mapper;

import com.wangzaiplus.test.pojo.LoginLog;

public interface LoginLogMapper {

    void insert(LoginLog loginLog);

    LoginLog selectByMsgId(String msgId);

}
