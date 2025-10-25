package model.entity;

public class NotaFrequencia {
    private long id;
    private long alunoId;
    private long disciplinaId;
    private String periodo; // ex: "1º Bim", "2º Bim"
    private double nota;
    private int frequenciaPercent; // 0..100

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getAlunoId() { return alunoId; }
    public void setAlunoId(long alunoId) { this.alunoId = alunoId; }

    public long getDisciplinaId() { return disciplinaId; }
    public void setDisciplinaId(long disciplinaId) { this.disciplinaId = disciplinaId; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    public int getFrequenciaPercent() { return frequenciaPercent; }
    public void setFrequenciaPercent(int frequenciaPercent) { this.frequenciaPercent = frequenciaPercent; }
}
