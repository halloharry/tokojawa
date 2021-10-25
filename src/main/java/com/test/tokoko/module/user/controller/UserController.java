package com.test.tokoko.module.user.controller;

import com.test.tokoko.common.payload.BaseResponse;
import com.test.tokoko.common.payload.CommonMessage;
import com.test.tokoko.model.User;
import com.test.tokoko.module.user.payload.AuthRequest;
import com.test.tokoko.module.user.payload.LoginRequest;
import com.test.tokoko.repository.UserRepository;
import com.test.tokoko.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public BaseResponse userRegister(@RequestBody AuthRequest authRequest) {
        try {
            String emailRgx = "^([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})";
            String passwordRgx = "^[a-zA-Z0-9@\\\\#$%&*()_+\\]\\[';:?.,!^-]{8,}$";

            boolean emailTrue = Pattern.matches(emailRgx, authRequest.getEmail());
            boolean passwordTrue = Pattern.matches(passwordRgx, authRequest.getPassword());
            if (emailTrue && passwordTrue) {
                User user = new User();
                user.setUsername(authRequest.getUsername());
                user.setEmail(authRequest.getEmail());
                user.setPassword(authRequest.getPassword());
                user.setIsDeleted(0);

                userRepository.save(user);
                return new BaseResponse(CommonMessage.REGISTER_SUCCESS, user.getEmail());
            } else {
                return new BaseResponse(CommonMessage.REGISTER_ERROR);
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
            return new BaseResponse(CommonMessage.REGISTER_ERROR);
        }
    }

    @PostMapping("/login")
    public BaseResponse userLogin(@RequestBody LoginRequest loginRequest) {
        System.out.println("ini auth : " + loginRequest);
        return userService.login(loginRequest);

    }

    @PostMapping("/login-token")
    public BaseResponse loginToken(@RequestBody LoginRequest loginRequest){
        return  userService.loginToken(loginRequest);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) throws IOException {

        String response = userService.forgotPassword(email);

        if (!response.startsWith("Invalid")) {
            response = "http://localhost:8080/user/reset-password?email=" + response;
        }
        return response;
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String password) throws IOException {
        return userService.resetPassword(email, password);
    }

    // ------------------------------------- TOKEN -------------------------------------------//

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
}
