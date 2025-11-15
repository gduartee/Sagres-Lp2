package model.entity;

import java.time.LocalDate;

public class FinanceTransaction {
    public enum Type { RECEITA, DESPESA }
    public enum Status { PENDENTE, PAGO }

    private Long id;
    private Type type;
    private String category;
    private String description;
    private double amount;
    private LocalDate date;
    private LocalDate dueDate; // para contas a pagar/receber
    private Status status;
    private boolean receiptIssued;

    public FinanceTransaction(){}

    public FinanceTransaction(Type type, String category, String description, double amount, LocalDate date, LocalDate dueDate){
        this.type = type;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.dueDate = dueDate;
        this.status = Status.PENDENTE;
        this.receiptIssued = false;
    }

    // getters/setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public Type getType(){ return type; }
    public void setType(Type t){ this.type = t; }

    public String getCategory(){ return category; }
    public void setCategory(String c){ this.category = c; }

    public String getDescription(){ return description; }
    public void setDescription(String d){ this.description = d; }

    public double getAmount(){ return amount; }
    public void setAmount(double a){ this.amount = a; }

    public java.time.LocalDate getDate(){ return date; }
    public void setDate(java.time.LocalDate d){ this.date = d; }

    public java.time.LocalDate getDueDate(){ return dueDate; }
    public void setDueDate(java.time.LocalDate d){ this.dueDate = d; }

    public Status getStatus(){ return status; }
    public void setStatus(Status s){ this.status = s; }

    public boolean isReceiptIssued(){ return receiptIssued; }
    public void setReceiptIssued(boolean v){ this.receiptIssued = v; }
}
