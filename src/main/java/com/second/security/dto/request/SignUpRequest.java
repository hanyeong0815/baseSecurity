package com.second.security.dto.request;

import com.second.security.domain.Users;
import com.second.security.domain.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class SignUpRequest {
    @Getter
    @AllArgsConstructor
    public static class SignUpReq {
        private String userName;
        private String password;
        private String email;
        private String name;
        private String nickname;
        private GenderType gender;
        private List<String> roles;

        public Users toEntity(PasswordEncoder encoder) {
            return Users.builder()
                    .username(this.userName)
                    .password(encoder.encode(this.password))
                    .email(this.email)
                    .name(this.name)
                    .nickname(this.nickname)
                    .genderType(this.gender)
                    .roles(this.roles)
                    .build();
        }
    }

}
