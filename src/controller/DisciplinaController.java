package controller;

import model.entity.Disciplina;
import model.service.DisciplinaService;

import java.util.List;

public class DisciplinaController {
    private final DisciplinaService service = new DisciplinaService();

    public List<Disciplina> listar(){ return service.listar(); }
    public List<Disciplina> buscar(String termo){ return service.buscar(termo); }
    public Disciplina salvar(Disciplina d){ return service.salvar(d); }
    public boolean excluir(long id){ return service.excluir(id); }
}
