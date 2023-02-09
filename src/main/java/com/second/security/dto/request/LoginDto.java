package com.second.security.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class LoginDto {
    @Getter
    @Setter
    public static class LoginRequest {
        private String username;
        private String password;

        @JsonCreator
        public LoginRequest(
                @JsonProperty(value = "username", required = true) String username,
                @JsonProperty(value = "password", required = true) String password
        ){
            this.username = username;
            this.password = password;
        }

        public static LoginRequest ConvertFromString(String jsonData) throws IOException {
            ObjectMapper om = new ObjectMapper();
            return om.readValue(jsonData, LoginRequest.class);
        }
    }
}
