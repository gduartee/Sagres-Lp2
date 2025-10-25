package model.repo;

import model.entity.Disciplina;

public class DisciplinaRepository extends JsonRepository<Disciplina> {
    public DisciplinaRepository() {
        super("data/disciplinas.json", Disciplina.class, Disciplina::getId, (d, id) -> d.setId(id));
    }
}