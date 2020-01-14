package com.messiyang.idempotency.service;



import com.messiyang.idempotency.pojo.MsgLog;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface MsgLogService {

    void updateStatus(String msgId, Integer status);

    MsgLog selectByMsgId(String msgId);

    List<MsgLog> selectTimeoutMsg();

    void updateTryCount(String msgId, Date tryTime);
}
