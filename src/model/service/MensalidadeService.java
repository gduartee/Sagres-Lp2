package model.service;

import model.entity.Mensalidade;
import model.entity.Mensalidade.Status;
import model.repo.MensalidadeRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MensalidadeService {
    private final MensalidadeRepository repo = new MensalidadeRepository();

    public List<Mensalidade> listar(){ return repo.findAll(); }

    public List<Mensalidade> listarPorAluno(long alunoId){
        return listar().stream().filter(m -> m.getAlunoId()==alunoId).collect(Collectors.toList());
    }

    // Gera mensalidades do ano letivo (RF028)
    public List<Mensalidade> gerarMensalidades(long alunoId, int ano, double valor, int diaVenc){
        List<Mensalidade> criadas = new ArrayList<>();
        for (int mes = 1; mes <= 12; mes++){
            Mensalidade m = new Mensalidade();
            m.setAlunoId(alunoId);
            m.setCompetenciaMes(mes);
            m.setCompetenciaAno(ano);
            m.setValor(valor);
            m.setVencimento(LocalDate.of(ano, mes, Math.min(diaVenc, 28)));
            m.setStatus(Status.PENDENTE);
            m.setMulta(0);
            m.setJuros(0);
            repo.save(m);
            criadas.add(m);
        }
        return criadas;
    }

    public Mensalidade registrarPagamento(long id){
        Mensalidade m = repo.findById(id);
        if(m==null) throw new IllegalArgumentException("Mensalidade não encontrada");
        m.setStatus(Status.PAGO);
        m.setMulta(0);
        m.setJuros(0);
        return repo.save(m);
    }

    // Aplica multa/juros em caso de atraso (RF031)
    public Mensalidade atualizarAtraso(long id, double multaPerc, double jurosAoMesPerc){
        Mensalidade m = repo.findById(id);
        if(m==null) throw new IllegalArgumentException("Mensalidade não encontrada");
        if(m.getStatus()==Status.PAGO) return m;
        if(LocalDate.now().isAfter(m.getVencimento())){
            m.setStatus(Status.ATRASADO);
            m.setMulta(m.getValor() * (multaPerc/100.0));
            m.setJuros(m.getValor() * (jurosAoMesPerc/100.0));
            return repo.save(m);
        }
        return m;
    }
}
