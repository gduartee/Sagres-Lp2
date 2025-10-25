package model.entity;

public class Matricula {
    private long id;
    private long alunoId;
    private long turmaId;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getAlunoId() { return alunoId; }
    public void setAlunoId(long alunoId) { this.alunoId = alunoId; }

    public long getTurmaId() { return turmaId; }
    public void setTurmaId(long turmaId) { this.turmaId = turmaId; }
}
