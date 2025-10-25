package model.entity;

import java.time.LocalDate;

public class Mensalidade {
    public enum Status { PENDENTE, PAGO, ATRASADO }

    private long id;
    private long alunoId;
    private int competenciaMes; // 1..12
    private int competenciaAno; // ex: 2025
    private double valor;
    private LocalDate vencimento;
    private Status status;
    private double multa; // aplicado em atraso
    private double juros;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getAlunoId() { return alunoId; }
    public void setAlunoId(long alunoId) { this.alunoId = alunoId; }

    public int getCompetenciaMes() { return competenciaMes; }
    public void setCompetenciaMes(int competenciaMes) { this.competenciaMes = competenciaMes; }

    public int getCompetenciaAno() { return competenciaAno; }
    public void setCompetenciaAno(int competenciaAno) { this.competenciaAno = competenciaAno; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public LocalDate getVencimento() { return vencimento; }
    public void setVencimento(LocalDate vencimento) { this.vencimento = vencimento; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public double getMulta() { return multa; }
    public void setMulta(double multa) { this.multa = multa; }

    public double getJuros() { return juros; }
    public void setJuros(double juros) { this.juros = juros; }
}
