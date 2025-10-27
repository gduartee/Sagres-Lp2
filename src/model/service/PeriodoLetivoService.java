package model.service;

import model.entity.PeriodoLetivo;
import model.repo.PeriodoLetivoRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

public class PeriodoLetivoService {
    private final PeriodoLetivoRepository repo = new PeriodoLetivoRepository();

    public List<PeriodoLetivo> listar() { return repo.findAll(); }
    
    public PeriodoLetivo salvar(PeriodoLetivo p) {
        if (p.getAnoEscolar() == null || p.getAnoEscolar().isBlank())
            throw new IllegalArgumentException("Ano Escolar é obrigatório");
        if (p.getNome() == null || p.getNome().isBlank())
            throw new IllegalArgumentException("Nome do Período é obrigatório");
        if (p.getTipo() == null)
            throw new IllegalArgumentException("Tipo de Período (Bimestre/Semestre) é obrigatório");
        
        return repo.save(p);
    }
    
    public List<String> listarAnosEscolares() {
        return listar().stream()
                       .map(PeriodoLetivo::getAnoEscolar)
                       .distinct() // Apenas valores únicos
                       .sorted(Comparator.reverseOrder()) // Ordem decrescente (mais recente primeiro)
                       .collect(Collectors.toList());
    }

    public boolean excluir(long id) { return repo.delete(id); }

    public List<PeriodoLetivo> buscar(String termo) {
        if (termo == null || termo.isBlank()) return listar();
        String t = termo.toLowerCase();
        return listar().stream().filter(p ->
            (p.getAnoEscolar() != null && p.getAnoEscolar().contains(termo)) ||
            (p.getNome() != null && p.getNome().toLowerCase().contains(t)) ||
            (p.getTipo() != null && p.getTipo().name().toLowerCase().contains(t)) )
            .collect(Collectors.toList());
    }
}
