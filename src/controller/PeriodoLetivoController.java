package controller;

import model.entity.PeriodoLetivo;
import model.service.PeriodoLetivoService;
import java.util.List;

public class PeriodoLetivoController {
    private final PeriodoLetivoService service = new PeriodoLetivoService();

    public List<PeriodoLetivo> listar(){ return service.listar(); }
    public List<String> listarAnosEscolares() { return service.listarAnosEscolares(); }
    public List<PeriodoLetivo> buscar(String termo){ return service.buscar(termo); }
    public PeriodoLetivo salvar(PeriodoLetivo p){ return service.salvar(p); }
    public boolean excluir(long id){ return service.excluir(id); }
}
