package model.service;

import model.entity.Professor;
import model.repo.ProfessorRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProfessorService {
    private final ProfessorRepository repo = new ProfessorRepository();

    public List<Professor> listar() { return repo.findAll(); }

    public List<Professor> buscar(String termo) {
        if (termo == null || termo.isBlank()) return listar();
        String t = termo.toLowerCase();
        return listar().stream().filter(p ->
            (p.getNomeCompleto()!=null && p.getNomeCompleto().toLowerCase().contains(t)) ||
            (p.getCpf()!=null && p.getCpf().contains(termo)) )
            .collect(Collectors.toList());
    }

    public Professor salvar(Professor p) {
        if (p.getNomeCompleto()==null || p.getNomeCompleto().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (p.getCpf()==null || p.getCpf().isBlank())
            throw new IllegalArgumentException("CPF é obrigatório");
        boolean cpfExiste = repo.findAll().stream()
                .anyMatch(x -> x.getCpf()!=null && x.getCpf().equals(p.getCpf()) && x.getId()!=p.getId());
        if (cpfExiste) throw new IllegalArgumentException("CPF já cadastrado para outro professor.");
        return repo.save(p);
    }

    public boolean excluir(long id) { return repo.delete(id); }
}
