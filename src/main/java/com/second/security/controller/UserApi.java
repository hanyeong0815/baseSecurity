package com.second.security.controller;

import com.second.security.dto.request.SignUpRequest.SignUpReq;
import com.second.security.dto.response.JwtResponse;
import com.second.security.dto.response.UsersResponse.UsersRes;
import com.second.security.service.AuthenticationService;
import com.second.security.service.UserService;
import com.second.security.utills.PasswordEncoderStorage;
import com.second.security.utills.StaticBlockSample;
import com.second.security.utills.pattern.Singleton;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final PasswordEncoderStorage encoder;

    @PostMapping("/signup")
    public boolean signUp(@RequestBody SignUpReq req) {
        return userService.signUp(req.toEntity(encoder.getPasswordEncoder()));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(Authentication authentication) {
        JwtResponse jwtResponse = authenticationService.issueUserJwt(authentication);
        return ResponseEntity.ok(jwtResponse);
    }

    @GetMapping("/home")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public UsersRes usersInfo(String username) {
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        System.out.println("1이 들어감");
        int num1 = singleton1.getNum(1);
        if (num1 == 1) {
            StaticBlockSample sample = new StaticBlockSample();
            System.out.println("1구간 num=> " + num1);
        }
        System.out.println("2가 들어감");
        int num2 = singleton2.getNum(2);


        if (num1 == 2) {
            StaticBlockSample sample = new StaticBlockSample();
            System.out.println("2구간 num=> " + num1);
        }
        if (num2 == 2) {
            StaticBlockSample sample = new StaticBlockSample();
            System.out.println("2구간 num2=> " + num2);
        }

        return userService.usersInfo(username);
    }

    @GetMapping("/delete-user")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteUser(Long userId) {
        return userService.deleteUser(userId);
    }
}
