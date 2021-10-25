package com.test.tokoko.service.impl;

import com.test.tokoko.common.payload.BaseResponse;
import com.test.tokoko.common.payload.CommonMessage;
import com.test.tokoko.model.User;
import com.test.tokoko.module.user.payload.AuthResponse;
import com.test.tokoko.module.user.payload.LoginRequest;
import com.test.tokoko.repository.UserRepository;
import com.test.tokoko.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    @Autowired
    UserRepository userRepository;

    @Override
    public BaseResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) {
            return new BaseResponse(CommonMessage.NOT_FOUND);
        } else if (user.getEmail().equals(loginRequest.getEmail()) && user.getPassword().equals(loginRequest.getPassword())) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setEmail(user.getEmail());
            authResponse.setUsername(user.getUsername());
            return new BaseResponse(CommonMessage.LOGIN_SUCCESS, authResponse);
        } else {
            return new BaseResponse(CommonMessage.LOGIN_ERROR);
        }
    }

    @Override
    public BaseResponse loginToken(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null) {
            String token = getJWTToken(user.getEmail());
            AuthResponse authResponse = new AuthResponse();
            authResponse.setEmail(user.getEmail());
            authResponse.setUsername(user.getUsername());
            authResponse.setToken(token);
            System.out.println("tokennnnnn : " + authResponse.getToken());
            return new BaseResponse(CommonMessage.LOGIN_SUCCESS, authResponse);
        } else {
            return new BaseResponse(CommonMessage.LOGIN_ERROR);
        }
    }

    @Override
    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
//            String token = getJWTToken(user.getEmail());
            return user.getEmail();
        } else {
            return "email yang ada masukan tidak ditemukan";
        }
    }

    @Override
    public String resetPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "password berhasil disimpan";
    }

    // -------------------------- TOKEN ---------------------------- //
    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return "Bearer " + token;
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
}
