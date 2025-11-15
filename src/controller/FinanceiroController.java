package controller;

import model.entity.FinanceTransaction;
import model.entity.FinanceTransaction.Type;
import model.service.FinanceService;
import view.financeiro.FinanceiroView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.io.File;

public class FinanceiroController {
    private final FinanceService service = new FinanceService();

    public FinanceiroController(){}

    public FinanceTransaction addTransaction(Type type, String category, String description, double amount, LocalDate date, LocalDate due){
        FinanceTransaction t = new FinanceTransaction(type, category, description, amount, date, due);
        return service.save(t);
    }

    public List<FinanceTransaction> listAll(){ return service.listAll(); }

    public FinanceTransaction update(FinanceTransaction t){ return service.save(t); }

    public boolean delete(long id){ return service.delete(id); }

    public Map<String,Double> balancete(LocalDate from, LocalDate to){ return service.balancete(from,to); }

    public void exportCsv(File out) throws Exception { service.exportCsv(out); }

    public List<FinanceTransaction> dueWithinDays(int days){ return service.dueWithinDays(days); }

    public List<FinanceTransaction> overdue(){ return service.overdue(); }
}
