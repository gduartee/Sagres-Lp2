package model.entity;

import java.util.List;

public class Turma {
    private long id;
    private String anoEscolar; // ex: "2025"
    private String serie;      // ex: "1º EM", "2º EM", "3º EM"
    private String nomeTurma;  // ex: "A", "B"
    private List<Long> disciplinasIds;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getAnoEscolar() { return anoEscolar; }
    public void setAnoEscolar(String anoEscolar) { this.anoEscolar = anoEscolar; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    public String getNomeTurma() { return nomeTurma; }
    public void setNomeTurma(String nomeTurma) { this.nomeTurma = nomeTurma; }

    public List<Long> getDisciplinasIds() { return disciplinasIds; }
    public void setDisciplinasIds(List<Long> disciplinasIds) { this.disciplinasIds = disciplinasIds; }
}
