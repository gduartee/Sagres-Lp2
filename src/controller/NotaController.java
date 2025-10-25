package controller;

import model.entity.NotaFrequencia;
import model.service.NotaService;

import java.util.List;

public class NotaController {
    private final NotaService service = new NotaService();

    public List<NotaFrequencia> listar(){ return service.listar(); }
    public List<NotaFrequencia> porAluno(long alunoId){ return service.listarPorAluno(alunoId); }
    public NotaFrequencia salvar(NotaFrequencia n){ return service.salvar(n); }
    public boolean excluir(long id){ return service.excluir(id); }
    public double mediaGeralAluno(long alunoId){ return service.mediaGeralAluno(alunoId); }
}
