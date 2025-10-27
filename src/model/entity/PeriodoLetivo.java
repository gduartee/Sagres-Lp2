package model.entity;

public class PeriodoLetivo {
    private long id;
    private String anoEscolar; // ex: "2025"
    private String nome; // ex: "1º Bimestre", "3º Trimestre"
    private TipoPeriodo tipo; // BIMESTRE, TRIMESTRE, SEMESTRE

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getAnoEscolar() { return anoEscolar; }
    public void setAnoEscolar(String anoEscolar) { this.anoEscolar = anoEscolar; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public TipoPeriodo getTipo() { return tipo; }
    public void setTipo(TipoPeriodo tipo) { this.tipo = tipo; }
}
