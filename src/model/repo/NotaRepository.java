package model.repo;

import model.entity.NotaFrequencia;

public class NotaRepository extends JsonRepository<NotaFrequencia> {
    public NotaRepository() {
        super("data/notas.json", NotaFrequencia.class, NotaFrequencia::getId, (n, id) -> n.setId(id));
    }
}