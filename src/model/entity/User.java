package model.entity;

public class User {
    private long id;
    private String username;     // único
    private String nomeCompleto;
    private String email;        // único
    private Role role;
    private String passwordHash; // SHA-256(hex)
    private String saltHex;      // salt(hex)
    private boolean ativo = true;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getSaltHex() { return saltHex; }
    public void setSaltHex(String saltHex) { this.saltHex = saltHex; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
