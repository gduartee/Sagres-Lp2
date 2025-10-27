package model.repo;

import model.entity.PeriodoLetivo;

public class PeriodoLetivoRepository extends JsonRepository<PeriodoLetivo> {
    public PeriodoLetivoRepository() {
        super("data/periodos.json", PeriodoLetivo.class, PeriodoLetivo::getId, (p, id) -> p.setId(id));
    }
}
