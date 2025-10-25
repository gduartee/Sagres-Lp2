package model.repo;

import model.entity.Aluno;

public class AlunoRepository extends JsonRepository<Aluno> {
    public AlunoRepository() {
        super("data/alunos.json", Aluno.class, Aluno::getId, (a, id) -> a.setId(id));
    }
}