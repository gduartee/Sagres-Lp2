package model.repo;

import model.entity.Turma;

public class TurmaRepository extends JsonRepository<Turma> {
    public TurmaRepository() {
        super("data/turmas.json", Turma.class, Turma::getId, (t, id) -> t.setId(id));
    }
}