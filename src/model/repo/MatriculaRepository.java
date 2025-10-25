package model.repo;

import model.entity.Matricula;

public class MatriculaRepository extends JsonRepository<Matricula> {
    public MatriculaRepository() {
        super("data/matriculas.json", Matricula.class, Matricula::getId, (m, id) -> m.setId(id));
    }
}