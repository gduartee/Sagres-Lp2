package model.entity;

import java.util.List;

public class Professor {
    private long id;
    private String nomeCompleto;
    private String cpf;
    private String rg;
    private String dataNascimento; // yyyy-MM-dd
    private String endereco;
    private String telefone;
    private String email;
    private String formacaoAcademica;
    private List<Long> disciplinasIds; // IDs de Disciplina atribuídas

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFormacaoAcademica() { return formacaoAcademica; }
    public void setFormacaoAcademica(String formacaoAcademica) { this.formacaoAcademica = formacaoAcademica; }

    public List<Long> getDisciplinasIds() { return disciplinasIds; }
    public void setDisciplinasIds(List<Long> disciplinasIds) { this.disciplinasIds = disciplinasIds; }
}
