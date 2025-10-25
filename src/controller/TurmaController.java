package controller;

import model.entity.Turma;
import model.service.TurmaService;

import java.util.List;

public class TurmaController {
    private final TurmaService service = new TurmaService();

    public List<Turma> listar(){ return service.listar(); }
    public Turma salvar(Turma t){ return service.salvar(t); }
    public boolean excluir(long id){ return service.excluir(id); }
}
