package view.disciplina;

import controller.DisciplinaController;
import model.entity.Disciplina;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DisciplinaListView extends JPanel {
    private final DisciplinaController controller = new DisciplinaController();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Nome","Código","Carga Horária"}, 0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField txtBusca = new JTextField();

    public DisciplinaListView(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Buscar (nome/código): "), BorderLayout.WEST);
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
            DisciplinaFormDialog dlg = new DisciplinaFormDialog(null);
            dlg.setVisible(true);
            if (dlg.getDisciplina() != null){
                controller.salvar(dlg.getDisciplina());
                carregar(null);
            }
        });
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            Disciplina d = controller.listar().stream().filter(x->x.getId()==id).findFirst().orElse(null);
            if(d==null) return;
            DisciplinaFormDialog dlg = new DisciplinaFormDialog(d);
            dlg.setVisible(true);
            if (dlg.getDisciplina() != null){
                controller.salvar(dlg.getDisciplina());
                carregar(null);
            }
        });
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            int ok = JOptionPane.showConfirmDialog(this, "Excluir disciplina "+id+"?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (ok==JOptionPane.YES_OPTION){
                controller.excluir(id);
                carregar(null);
            }
        });

        carregar(null);
    }

    private void carregar(String termo){
        model.setRowCount(0);
        List<Disciplina> lista = (termo==null || termo.isBlank()) ? controller.listar() : controller.buscar(termo);
        for (Disciplina d : lista){
            model.addRow(new Object[]{d.getId(), d.getNome(), d.getCodigo(), d.getCargaHoraria()});
        }
    }
}
