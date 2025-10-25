package model.service;

import model.entity.Matricula;
import model.repo.MatriculaRepository;

import java.util.List;

public class MatriculaService {
    private final MatriculaRepository repo = new MatriculaRepository();

    public List<Matricula> listar(){ return repo.findAll(); }

    public Matricula salvar(Matricula m){
        if(m.getAlunoId()<=0) throw new IllegalArgumentException("Aluno inválido");
        if(m.getTurmaId()<=0) throw new IllegalArgumentException("Turma inválida");
        return repo.save(m);
    }

    public boolean excluir(long id){ return repo.delete(id); }

    // "Transferência": atualiza a turma de uma matrícula existente
    public Matricula transferir(long matriculaId, long novaTurmaId){
        Matricula m = repo.findById(matriculaId);
        if(m==null) throw new IllegalArgumentException("Matrícula não encontrada");
        m.setTurmaId(novaTurmaId);
        return repo.save(m);
    }
}
