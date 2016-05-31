package com.tangr.httpapplication;

/**
 * Created by tangr on 2016/5/31.
 */
public class User {

    /**
     * id : 2
     * account : stay4it
     * email : stay4it@163.com
     * username : stay
     * password : 123456
     * avatar : uploads/avatar/dee0201e356c5dc426cd394042f97927.jpg
     * token : I8FyNDUx0WcBZng7g0USesXvy3f6haZ7V1HFvx7gaRs=
     */

    private String id;
    private String account;
    private String email;
    private String username;
    private String password;
    private String avatar;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
