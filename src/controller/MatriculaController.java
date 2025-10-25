package controller;

import model.entity.Matricula;
import model.service.MatriculaService;

import java.util.List;

public class MatriculaController {
    private final MatriculaService service = new MatriculaService();

    public List<Matricula> listar(){ return service.listar(); }
    public Matricula salvar(Matricula m){ return service.salvar(m); }
    public boolean excluir(long id){ return service.excluir(id); }
    public Matricula transferir(long matriculaId, long novaTurmaId){ return service.transferir(matriculaId, novaTurmaId); }
}
