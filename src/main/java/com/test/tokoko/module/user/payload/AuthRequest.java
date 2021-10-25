package com.test.tokoko.module.user.payload;

import lombok.Data;

@Data
public class AuthRequest {
    String username;
    String email;
    String password;
}
