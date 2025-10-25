package controller;

import model.entity.Mensalidade;
import model.service.MensalidadeService;

import java.util.List;

public class MensalidadeController {
    private final MensalidadeService service = new MensalidadeService();

    public List<Mensalidade> listar(){ return service.listar(); }
    public List<Mensalidade> listarPorAluno(long alunoId){ return service.listarPorAluno(alunoId); }
    public List<Mensalidade> gerarMensalidades(long alunoId, int ano, double valor, int diaVenc){
        return service.gerarMensalidades(alunoId, ano, valor, diaVenc);
    }
    public Mensalidade registrarPagamento(long id){ return service.registrarPagamento(id); }
    public Mensalidade atualizarAtraso(long id, double multaPerc, double jurosAoMesPerc){
        return service.atualizarAtraso(id, multaPerc, jurosAoMesPerc);
    }
}
