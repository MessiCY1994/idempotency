package com.messiyang.idempotency.service;

import com.messiyang.idempotency.common.ServerResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface TokenService {

    ServerResponse createToken();

    void checkToken(HttpServletRequest request);

}
