package model.repo;

import model.entity.FinanceTransaction;

public class FinanceRepository extends JsonRepository<FinanceTransaction> {
    public FinanceRepository(){
        super("data/finance_transactions.json", FinanceTransaction.class, FinanceTransaction::getId, (m,id)->m.setId(id));
    }
}
