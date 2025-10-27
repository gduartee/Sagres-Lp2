package view.periodo;

import controller.PeriodoLetivoController;
import model.entity.PeriodoLetivo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PeriodoLetivoListView extends JPanel {
    private final PeriodoLetivoController controller = new PeriodoLetivoController();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Ano Escolar","Nome","Tipo"}, 0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField txtBusca = new JTextField();

    public PeriodoLetivoListView(){
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Buscar (ano/nome/tipo): "), BorderLayout.WEST);
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
            PeriodoLetivoFormDialog dlg = new PeriodoLetivoFormDialog(null);
            dlg.setVisible(true);
            if (dlg.getPeriodo()!=null){
                controller.salvar(dlg.getPeriodo());
                carregar(null);
            }
        });
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            PeriodoLetivo p = controller.listar().stream().filter(x->x.getId()==id).findFirst().orElse(null);
            if (p==null) return;
            PeriodoLetivoFormDialog dlg = new PeriodoLetivoFormDialog(p);
            dlg.setVisible(true);
            if (dlg.getPeriodo()!=null){
                controller.salvar(dlg.getPeriodo());
                carregar(null);
            }
        });
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            int ok = JOptionPane.showConfirmDialog(this, "Excluir período "+id+"?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (ok==JOptionPane.YES_OPTION){
                controller.excluir(id);
                carregar(null);
            }
        });

        carregar(null);
    }

    private void carregar(String termo){
        model.setRowCount(0);
        List<PeriodoLetivo> lista = (termo==null || termo.isBlank()) ? controller.listar() : controller.buscar(termo);
        for (PeriodoLetivo p: lista){
            model.addRow(new Object[]{p.getId(), p.getAnoEscolar(), p.getNome(), p.getTipo()});
        }
    }
}
