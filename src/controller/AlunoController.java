package controller;

import model.entity.Aluno;
import model.service.AlunoService;

import java.util.List;

public class AlunoController {
    private final AlunoService service = new AlunoService();

    public List<Aluno> listar(){ return service.listar(); }
    public List<Aluno> buscar(String termo){ return service.buscar(termo); }
    public Aluno salvar(Aluno a){ return service.salvar(a); }
    public boolean excluir(long id){ return service.excluir(id); }
}
