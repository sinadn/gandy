package com.example.gandy.payload.response;

import java.util.List;

public class JwtResponse {

        private String token;
        private String type = "Bearer";
        private String refreshToken;
        private Long id;
        private String mobile;
        private List<String> roles;

        public JwtResponse(String accessToken, String refreshToken, Long id, String mobile, List<String> roles) {
            this.token = accessToken;
            this.refreshToken = refreshToken;
            this.id = id;
            this.mobile = mobile;
            this.roles = roles;
        }

        // getters and setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }



    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
