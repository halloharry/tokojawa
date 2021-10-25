package com.test.tokoko.service;

import com.test.tokoko.common.payload.BaseResponse;
import com.test.tokoko.module.user.payload.AuthRequest;
import com.test.tokoko.module.user.payload.LoginRequest;

public interface UserService {
    BaseResponse login(LoginRequest loginRequest);

    BaseResponse loginToken(LoginRequest loginRequest);

    String forgotPassword(String email);

    String resetPassword(String email, String password);

}
