package model.service;

import model.entity.FinanceTransaction;
import model.entity.FinanceTransaction.Status;
import model.entity.FinanceTransaction.Type;
import model.repo.FinanceRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class FinanceService {
    private final FinanceRepository repo = new FinanceRepository();

    public FinanceTransaction save(FinanceTransaction t){
        return repo.save(t);
    }

    public List<FinanceTransaction> listAll(){
        return repo.findAll();
    }

    public List<FinanceTransaction> findByType(Type type){
        return repo.findAll().stream().filter(t->t.getType()==type).collect(Collectors.toList());
    }

    public List<FinanceTransaction> between(LocalDate from, LocalDate to){
        return repo.findAll().stream().filter(t->{
            LocalDate d = t.getDate();
            return (d!=null && !d.isBefore(from) && !d.isAfter(to));
        }).collect(Collectors.toList());
    }

    public double totalBetween(LocalDate from, LocalDate to, Type type){
        return between(from,to).stream().filter(t->t.getType()==type).mapToDouble(FinanceTransaction::getAmount).sum();
    }

    public Map<String, Double> balancete(LocalDate from, LocalDate to){
        double receitas = totalBetween(from,to, Type.RECEITA);
        double despesas = totalBetween(from,to, Type.DESPESA);
        Map<String,Double> m = new LinkedHashMap<>();
        m.put("receitas", receitas);
        m.put("despesas", despesas);
        m.put("resultado", receitas - despesas);
        return m;
    }

    public List<FinanceTransaction> dueWithinDays(int days){
        LocalDate now = LocalDate.now();
        LocalDate limit = now.plusDays(days);
        return repo.findAll().stream().filter(t->t.getDueDate()!=null && ( (t.getDueDate().isAfter(now) || t.getDueDate().isEqual(now)) && (t.getDueDate().isBefore(limit) || t.getDueDate().isEqual(limit)) )).collect(Collectors.toList());
    }

    public List<FinanceTransaction> overdue(){
        LocalDate now = LocalDate.now();
        return repo.findAll().stream().filter(t->t.getDueDate()!=null && t.getDueDate().isBefore(now) && t.getStatus()!=Status.PAGO).collect(Collectors.toList());
    }

    public void exportCsv(File outFile) throws IOException{
        List<FinanceTransaction> all = repo.findAll();
        try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
            pw.println("id;type;category;description;amount;date;dueDate;status;receiptIssued");
            for(FinanceTransaction t: all){
                pw.printf("%d;%s;%s;%s;%.2f;%s;%s;%s;%b\n",
                    t.getId(),
                    t.getType(),
                    t.getCategory(),
                    t.getDescription()!=null ? t.getDescription().replaceAll("[\\r\\n;]"," ") : "",
                    t.getAmount(),
                    t.getDate()!=null ? t.getDate().toString() : "",
                    t.getDueDate()!=null ? t.getDueDate().toString() : "",
                    t.getStatus(),
                    t.isReceiptIssued()
                );
            }
        }
    }


public boolean delete(long id){
    return repo.delete(id);
}
}
