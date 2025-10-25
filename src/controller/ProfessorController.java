package controller;

import model.entity.Professor;
import model.service.ProfessorService;

import java.util.List;

public class ProfessorController {
    private final ProfessorService service = new ProfessorService();

    public List<Professor> listar(){ return service.listar(); }
    public List<Professor> buscar(String termo){ return service.buscar(termo); }
    public Professor salvar(Professor p){ return service.salvar(p); }
    public boolean excluir(long id){ return service.excluir(id); }
}
