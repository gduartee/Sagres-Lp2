package model.service;

import model.entity.Turma;
import model.repo.TurmaRepository;
import java.util.List;

public class TurmaService {
    private final TurmaRepository repo = new TurmaRepository();
    public List<Turma> listar(){ return repo.findAll(); }
    public Turma salvar(Turma t){
        if(t.getAnoEscolar()==null || t.getAnoEscolar().isBlank()) throw new IllegalArgumentException("Ano escolar obrigatório");
        if(t.getSerie()==null || t.getSerie().isBlank()) throw new IllegalArgumentException("Série obrigatória");
        if(t.getNomeTurma()==null || t.getNomeTurma().isBlank()) throw new IllegalArgumentException("Nome da turma obrigatório");
        return repo.save(t);
    }
    public boolean excluir(long id){ return repo.delete(id); }
}
