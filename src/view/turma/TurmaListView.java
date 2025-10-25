package view.turma;

import controller.TurmaController;
import model.entity.Turma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TurmaListView extends JPanel {
    private final TurmaController controller = new TurmaController();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Ano","Série","Turma"}, 0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField txtBusca = new JTextField(); // busca simples por série/ano/turma

    public TurmaListView(){
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Buscar (ano/série/turma): "), BorderLayout.WEST);
        top.add(txtBusca, BorderLayout.CENTER);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JPanel buttons = new JPanel();
        buttons.add(btnBuscar); buttons.add(btnNovo); buttons.add(btnEditar); buttons.add(btnExcluir);
        top.add(buttons, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> carregar(txtBusca.getText().trim()));
        btnNovo.addActionListener(e -> {
            TurmaFormDialog dlg = new TurmaFormDialog(null);
            dlg.setVisible(true);
            if (dlg.getTurma()!=null){
                controller.salvar(dlg.getTurma());
                carregar(null);
            }
        });
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            Turma t = controller.listar().stream().filter(x->x.getId()==id).findFirst().orElse(null);
            if (t==null) return;
            TurmaFormDialog dlg = new TurmaFormDialog(t);
            dlg.setVisible(true);
            if (dlg.getTurma()!=null){
                controller.salvar(dlg.getTurma());
                carregar(null);
            }
        });
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            int ok = JOptionPane.showConfirmDialog(this, "Excluir turma "+id+"?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (ok==JOptionPane.YES_OPTION){
                controller.excluir(id);
                carregar(null);
            }
        });

        carregar(null);
    }

    private void carregar(String termo){
        model.setRowCount(0);
        List<Turma> turmas = controller.listar();
        for (Turma t: turmas){
            if (termo==null || termo.isBlank()
                    || (t.getAnoEscolar()!=null && t.getAnoEscolar().toLowerCase().contains(termo.toLowerCase()))
                    || (t.getSerie()!=null && t.getSerie().toLowerCase().contains(termo.toLowerCase()))
                    || (t.getNomeTurma()!=null && t.getNomeTurma().toLowerCase().contains(termo.toLowerCase())) ){
                model.addRow(new Object[]{t.getId(), t.getAnoEscolar(), t.getSerie(), t.getNomeTurma()});
            }
        }
    }
}
