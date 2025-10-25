package controller;

import model.entity.Role;
import model.entity.User;
import model.service.UserService;

import java.util.List;

public class UserController {
    private final UserService service = new UserService();

    public List<User> listar(){ return service.listar(); }
    public User salvarNovo(String username, String nome, String email, Role role, String senha){
        return service.salvarNovo(username, nome, email, role, senha);
    }
    public void alterarSenha(long userId, String novaSenha){ service.alterarSenha(userId, novaSenha); }
}
