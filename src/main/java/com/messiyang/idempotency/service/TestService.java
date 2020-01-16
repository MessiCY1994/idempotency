package com.messiyang.idempotency.service;


import com.messiyang.idempotency.common.ServerResponse;
import com.messiyang.idempotency.pojo.Mail;

public interface TestService {

    ServerResponse testIdempotence();

    ServerResponse accessLimit();

    ServerResponse send(Mail mail);
}
