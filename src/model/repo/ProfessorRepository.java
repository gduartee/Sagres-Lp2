package model.repo;

import model.entity.Professor;

public class ProfessorRepository extends JsonRepository<Professor> {
    public ProfessorRepository() {
        super("data/professores.json", Professor.class, Professor::getId, (p, id) -> p.setId(id));
    }
}