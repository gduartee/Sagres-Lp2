package controller;

import model.entity.User;
import model.service.AuthService;

public class AuthController {
    private final AuthService auth = new AuthService();

    public User login(String username, String password){ return auth.login(username, password); }
    public void logout(){ auth.logout(); }
}
