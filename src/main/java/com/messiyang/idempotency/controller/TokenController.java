package com.messiyang.idempotency.controller;


import com.messiyang.idempotency.common.ServerResponse;
import com.messiyang.idempotency.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/create")
    public ServerResponse token() {
        return tokenService.createToken();
    }

}
