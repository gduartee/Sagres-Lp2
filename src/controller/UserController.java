package controller;

import model.entity.Role;
import model.entity.User;
import model.service.UserService;

import java.util.List;

public class UserController {
    private final UserService service = new UserService();

    public List<User> listar(){ return service.listar(); }
    public List<User> buscar(String termo){ return service.buscar(termo); }
    public User salvarNovo(String username, String nome, String email, Role role, String senha){
        return service.salvarNovo(username, nome, email, role, senha);
    }
    public User salvar(User u){ return service.salvar(u); }
    public void alterarSenha(long userId, String novaSenha){ service.alterarSenha(userId, novaSenha); }
    public boolean excluir(long id){ return service.excluir(id); }
}
