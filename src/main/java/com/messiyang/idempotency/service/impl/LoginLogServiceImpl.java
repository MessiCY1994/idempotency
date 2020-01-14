package com.messiyang.idempotency.service.impl;


import com.messiyang.idempotency.mapper.LoginLogMapper;
import com.messiyang.idempotency.pojo.LoginLog;
import com.messiyang.idempotency.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void insert(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }

    @Override
    public LoginLog selectByMsgId(String msgId) {
        return loginLogMapper.selectByMsgId(msgId);
    }

}
