package model.service;

import model.entity.Aluno;
import model.repo.AlunoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AlunoService {
    private final AlunoRepository repo = new AlunoRepository();

    public List<Aluno> listar() { return repo.findAll(); }

    public List<Aluno> buscar(String termo) {
        if (termo == null || termo.isBlank()) return listar();
        String t = termo.toLowerCase();
        return listar().stream().filter(a ->
            (a.getNomeCompleto()!=null && a.getNomeCompleto().toLowerCase().contains(t)) ||
            (a.getCpf()!=null && a.getCpf().contains(termo)) )
            .collect(Collectors.toList());
    }

    public Aluno salvar(Aluno a) {
        validar(a);
        // Evita duplicidade de CPF
        boolean cpfExiste = repo.findAll().stream()
                .anyMatch(x -> x.getCpf()!=null && x.getCpf().equals(a.getCpf()) && x.getId()!=a.getId());
        if (cpfExiste) throw new IllegalArgumentException("CPF já cadastrado para outro aluno.");
        return repo.save(a);
    }

    public boolean excluir(long id) { return repo.delete(id); }

    private void validar(Aluno a) {
        if (a.getNomeCompleto()==null || a.getNomeCompleto().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (a.getCpf()==null || a.getCpf().isBlank())
            throw new IllegalArgumentException("CPF é obrigatório");
    }
}
