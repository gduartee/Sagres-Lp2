package model.repo;

import model.entity.Mensalidade;

public class MensalidadeRepository extends JsonRepository<Mensalidade> {
    public MensalidadeRepository() {
        super("data/mensalidades.json", Mensalidade.class, Mensalidade::getId, (m, id) -> m.setId(id));
    }
}