package com.messiyang.idempotency.pojo;

import lombok.*;

@Data
public class User {

    private Long id;
    private String username;
    private String password;

}
