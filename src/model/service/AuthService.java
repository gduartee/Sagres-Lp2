package model.service;

import infra.PasswordUtil;
import model.entity.User;

public class AuthService {
    private final UserService userService = new UserService();

    public User login(String username, String password){
        userService.seedAdminIfEmpty();
        User u = userService.findByUsername(username);
        if (u==null || !u.isAtivo()) throw new IllegalArgumentException("Credenciais inválidas");
        boolean ok = PasswordUtil.verify(u.getSaltHex(), password, u.getPasswordHash());
        if (!ok) throw new IllegalArgumentException("Credenciais inválidas");
        Session.setCurrentUser(u);
        return u;
    }

    public void logout(){
        Session.clear();
    }
}
