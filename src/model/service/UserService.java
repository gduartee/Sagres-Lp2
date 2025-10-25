package model.service;

import infra.PasswordUtil;
import model.entity.Role;
import model.entity.User;
import model.repo.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository repo = new UserRepository();

    public List<User> listar() { return repo.findAll(); }

    public User findByUsername(String username){
        return repo.findAll().stream()
                .filter(u -> u.getUsername()!=null && u.getUsername().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }

    public User findByEmail(String email){
        return repo.findAll().stream()
                .filter(u -> u.getEmail()!=null && u.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
    }

    public User salvarNovo(String username, String nome, String email, Role role, String rawPassword){
        if (username==null || username.isBlank()) throw new IllegalArgumentException("Username obrigatório");
        if (email==null || email.isBlank()) throw new IllegalArgumentException("Email obrigatório");
        if (rawPassword==null || rawPassword.isBlank()) throw new IllegalArgumentException("Senha obrigatória");
        if (findByUsername(username)!=null) throw new IllegalArgumentException("Username já cadastrado");
        if (findByEmail(email)!=null) throw new IllegalArgumentException("Email já cadastrado");

        String salt = PasswordUtil.randomSaltHex(16);
        String hash = PasswordUtil.sha256Hex(salt, rawPassword);

        User u = new User();
        u.setUsername(username);
        u.setNomeCompleto(nome);
        u.setEmail(email);
        u.setRole(role);
        u.setSaltHex(salt);
        u.setPasswordHash(hash);
        u.setAtivo(true);
        return repo.save(u);
    }

    public void alterarSenha(long userId, String novaSenha){
        if (novaSenha==null || novaSenha.isBlank()) throw new IllegalArgumentException("Senha obrigatória");
        User u = repo.findById(userId);
        if (u==null) throw new IllegalArgumentException("Usuário não encontrado");
        String salt = PasswordUtil.randomSaltHex(16);
        String hash = PasswordUtil.sha256Hex(salt, novaSenha);
        u.setSaltHex(salt);
        u.setPasswordHash(hash);
        repo.save(u);
    }

    /** Cria admin padrão se a base estiver vazia. */
    public void seedAdminIfEmpty(){
        if (repo.findAll().isEmpty()){
            salvarNovo("admin", "Administrador", "admin@local", Role.ADMIN, "admin");
        }
    }
}
