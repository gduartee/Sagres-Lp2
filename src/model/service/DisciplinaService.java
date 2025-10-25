package model.service;

import model.entity.Disciplina;
import model.repo.DisciplinaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DisciplinaService {
    private final DisciplinaRepository repo = new DisciplinaRepository();

    public List<Disciplina> listar() { return repo.findAll(); }

    public List<Disciplina> buscar(String termo) {
        if (termo == null || termo.isBlank()) return listar();
        String t = termo.toLowerCase();
        return listar().stream().filter(d ->
            (d.getNome()!=null && d.getNome().toLowerCase().contains(t)) ||
            (d.getCodigo()!=null && d.getCodigo().toLowerCase().contains(t)) )
            .collect(Collectors.toList());
    }

    public Disciplina salvar(Disciplina d) {
        if (d.getNome()==null || d.getNome().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (d.getCodigo()==null || d.getCodigo().isBlank())
            throw new IllegalArgumentException("Código é obrigatório");
        return repo.save(d);
    }

    public boolean excluir(long id) { return repo.delete(id); }
}
