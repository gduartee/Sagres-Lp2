package model.service;

import model.entity.NotaFrequencia;
import model.repo.NotaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class NotaService {
    private final NotaRepository repo = new NotaRepository();

    public List<NotaFrequencia> listar(){ return repo.findAll(); }

    public List<NotaFrequencia> listarPorAluno(long alunoId){
        return listar().stream().filter(n -> n.getAlunoId()==alunoId).collect(Collectors.toList());
    }

    public NotaFrequencia salvar(NotaFrequencia n){
        if(n.getAlunoId()<=0) throw new IllegalArgumentException("Aluno inválido");
        if(n.getDisciplinaId()<=0) throw new IllegalArgumentException("Disciplina inválida");
        if(n.getPeriodo()==null || n.getPeriodo().isBlank()) throw new IllegalArgumentException("Período obrigatório");
        if(n.getFrequenciaPercent()<0 || n.getFrequenciaPercent()>100) throw new IllegalArgumentException("Frequência deve ser 0..100");
        if(n.getNota()<0 || n.getNota()>10) throw new IllegalArgumentException("Nota deve ser 0..10");
        return repo.save(n);
    }

    public boolean excluir(long id){ return repo.delete(id); }

    public double mediaGeralAluno(long alunoId){
        List<NotaFrequencia> lst = listarPorAluno(alunoId);
        if(lst.isEmpty()) return 0.0;
        return lst.stream().mapToDouble(NotaFrequencia::getNota).average().orElse(0.0);
    }
}
