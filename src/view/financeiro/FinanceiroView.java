package view.financeiro;

import controller.FinanceiroController;
import model.entity.FinanceTransaction;
import model.entity.FinanceTransaction.Type;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.io.File;

public class FinanceiroView extends JPanel {

    private FinanceiroController controller = new FinanceiroController();
    private JTable table;
    private DefaultTableModel model;

    public FinanceiroView(){
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"ID","Tipo","Categoria","Descrição","Valor","Data","Venc."},0){
            public boolean isCellEditable(int r,int c){ return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnExport = new JButton("Exportar CSV");
        JButton btnFluxo = new JButton("Fluxo Caixa");
        JButton btnAlert = new JButton("Alertas");
        top.add(btnNovo); top.add(btnEditar); top.add(btnExcluir); top.add(btnExport); top.add(btnFluxo); top.add(btnAlert);
        add(top, BorderLayout.NORTH);

        btnNovo.addActionListener(e -> onNovo());
        btnEditar.addActionListener(e -> onEditar());
        btnExcluir.addActionListener(e -> onExcluir());
        btnExport.addActionListener(e -> onExport());
        btnFluxo.addActionListener(e -> onFluxo());
        btnAlert.addActionListener(e -> onAlert());

        refreshTable();
    }

    private void onNovo(){
        String[] options = {"RECEITA","DESPESA"};
        String tipo = (String) JOptionPane.showInputDialog(this, "Tipo:", "Nova Transação", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if(tipo==null) return;
        String cat = JOptionPane.showInputDialog(this, "Categoria:");
        if(cat==null) return;
        String desc = JOptionPane.showInputDialog(this, "Descrição:");
        if(desc==null) desc = "";
        String val = JOptionPane.showInputDialog(this, "Valor (ex: 123.45):");
        if(val==null) return;
        double v;
        try{ v = Double.parseDouble(val); } catch(Exception ex){ JOptionPane.showMessageDialog(this, "Valor inválido"); return; }
        String dateStr = JOptionPane.showInputDialog(this, "Data (YYYY-MM-DD) - deixe em branco para hoje:");
        LocalDate date = (dateStr==null || dateStr.trim().isEmpty()) ? LocalDate.now() : LocalDate.parse(dateStr.trim());
        String dueStr = JOptionPane.showInputDialog(this, "Vencimento (YYYY-MM-DD) - opcional:");
        LocalDate due = (dueStr==null || dueStr.trim().isEmpty()) ? null : LocalDate.parse(dueStr.trim());
        controller.addTransaction(Type.valueOf(tipo), cat, desc, v, date, due);
        refreshTable();
    }

    private void onEditar(){
        int row = table.getSelectedRow();
        if(row < 0){ JOptionPane.showMessageDialog(this, "Selecione uma linha para editar."); return; }
        Long id = Long.valueOf(model.getValueAt(row,0).toString());
        FinanceTransaction t = controller.listAll().stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);
        if(t == null){ JOptionPane.showMessageDialog(this, "Registro não encontrado."); return; }

        String[] options = {"RECEITA","DESPESA"};
        String tipo = (String) JOptionPane.showInputDialog(this, "Tipo:", "Editar Transação", JOptionPane.PLAIN_MESSAGE, null, options, t.getType().toString());
        if(tipo==null) return;
        String cat = JOptionPane.showInputDialog(this, "Categoria:", t.getCategory());
        if(cat==null) return;
        String desc = JOptionPane.showInputDialog(this, "Descrição:", t.getDescription());
        if(desc==null) desc="";
        String val = JOptionPane.showInputDialog(this, "Valor:", String.format("%.2f", t.getAmount()));
        if(val==null) return;
        double v; try{ v=Double.parseDouble(val);}catch(Exception ex){ JOptionPane.showMessageDialog(this,"Valor inválido"); return; }
        String dateStr = JOptionPane.showInputDialog(this, "Data (YYYY-MM-DD):", t.getDate()!=null?t.getDate().toString():"");
        LocalDate date = (dateStr==null||dateStr.trim().isEmpty())? t.getDate(): LocalDate.parse(dateStr.trim());
        String dueStr = JOptionPane.showInputDialog(this, "Vencimento (YYYY-MM-DD):", t.getDueDate()!=null?t.getDueDate().toString():"");
        LocalDate due = (dueStr==null||dueStr.trim().isEmpty())? t.getDueDate(): LocalDate.parse(dueStr.trim());

        t.setType(Type.valueOf(tipo));
        t.setCategory(cat); t.setDescription(desc); t.setAmount(v); t.setDate(date); t.setDueDate(due);
        controller.update(t);
        refreshTable();
    }

    private void onExcluir(){
        int row = table.getSelectedRow();
        if(row < 0){ JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir."); return; }
        Long id = Long.valueOf(model.getValueAt(row,0).toString());
        int ok = JOptionPane.showConfirmDialog(this, "Confirma exclusão do registro ID "+id+"?", "Excluir", JOptionPane.YES_NO_OPTION);
        if(ok==JOptionPane.YES_OPTION){
            controller.delete(id);
            refreshTable();
        }
    }

    private void onExport(){
        JFileChooser fc = new JFileChooser();
        int ret = fc.showSaveDialog(this);
        if(ret==JFileChooser.APPROVE_OPTION){
            File f = fc.getSelectedFile();
            try{
                controller.exportCsv(f);
                JOptionPane.showMessageDialog(this, "Exportado com sucesso: " + f.getAbsolutePath());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Erro ao exportar: " + ex.getMessage());
            }
        }
    }

    private void onFluxo(){
        String from = JOptionPane.showInputDialog(this, "Data inicial (YYYY-MM-DD):", LocalDate.now().withDayOfMonth(1).toString());
        if(from==null) return;
        String to = JOptionPane.showInputDialog(this, "Data final (YYYY-MM-DD):", LocalDate.now().toString());
        if(to==null) return;
        try{
            java.time.LocalDate f = java.time.LocalDate.parse(from);
            java.time.LocalDate t = java.time.LocalDate.parse(to);
            java.util.Map<String,Double> bal = controller.balancete(f,t);
            String msg = String.format("Receitas: %.2f\nDespesas: %.2f\nResultado: %.2f", bal.get("receitas"), bal.get("despesas"), bal.get("resultado"));
            JOptionPane.showMessageDialog(this, msg, "Balancete", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Datas inválidas");
        }
    }

    private void onAlert(){
        List<FinanceTransaction> due = controller.dueWithinDays(7);
        List<FinanceTransaction> over = controller.overdue();
        StringBuilder sb = new StringBuilder();
        sb.append("Vencimentos próximos (7 dias):\n");
        for(FinanceTransaction t : due){
            sb.append(String.format("%d - %s - %s - %.2f - venc: %s\n", t.getId(), t.getType(), t.getCategory(), t.getAmount(), t.getDueDate()));
        }
        sb.append("\nAtrasados:\n");
        for(FinanceTransaction t : over){
            sb.append(String.format("%d - %s - %s - %.2f - venc: %s\n", t.getId(), t.getType(), t.getCategory(), t.getAmount(), t.getDueDate()));
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Alertas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshTable(){
        model.setRowCount(0);
        for(FinanceTransaction t : controller.listAll()){
            model.addRow(new Object[]{
                t.getId(),
                t.getType(),
                t.getCategory(),
                t.getDescription(),
                String.format("%.2f", t.getAmount()),
                t.getDate(),
                t.getDueDate()
            });
        }
    }
}
