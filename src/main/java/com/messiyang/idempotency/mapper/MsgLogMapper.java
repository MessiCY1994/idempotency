package com.messiyang.idempotency.mapper;

import com.messiyang.idempotency.pojo.MsgLog;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MsgLogMapper {

    void insert(MsgLog msgLog);

    void updateStatus(MsgLog msgLog);

    List<MsgLog> selectTimeoutMsg();

    void updateTryCount(MsgLog msgLog);

    MsgLog selectByPrimaryKey(String msgId);

}
