package model.entity;

import java.util.List;

public class Disciplina {
    private long id;
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private String descricao;
    private List<String> conteudoProgramatico;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public List<String> getConteudoProgramatico() { return conteudoProgramatico; }
    public void setConteudoProgramatico(List<String> conteudoProgramatico) { this.conteudoProgramatico = conteudoProgramatico; }
}
