package com.test.tokoko.module.user.payload;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
