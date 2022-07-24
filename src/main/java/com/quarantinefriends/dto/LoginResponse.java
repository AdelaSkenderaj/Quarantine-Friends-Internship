package com.quarantinefriends.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginResponse {
        private UserDTO user;
        private String token;

        public LoginResponse(UserDTO user, String token) {
            this.user = user;
            this.token = token;
        }

        public LoginResponse(UserDTO user) {
            this.user = user;
        }
}


