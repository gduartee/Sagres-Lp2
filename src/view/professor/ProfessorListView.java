package view.professor;

import controller.ProfessorController;
import model.entity.Professor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProfessorListView extends JPanel {
    private final ProfessorController controller = new ProfessorController();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Nome","CPF","Telefone","Email"}, 0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField txtBusca = new JTextField();

    public ProfessorListView(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Buscar (nome/CPF): "), BorderLayout.WEST);
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
            ProfessorFormDialog dlg = new ProfessorFormDialog(null);
            dlg.setVisible(true);
            if (dlg.getProfessor() != null){
                controller.salvar(dlg.getProfessor());
                carregar(null);
            }
        });
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            Professor p = controller.listar().stream().filter(x->x.getId()==id).findFirst().orElse(null);
            if(p==null) return;
            ProfessorFormDialog dlg = new ProfessorFormDialog(p);
            dlg.setVisible(true);
            if (dlg.getProfessor() != null){
                controller.salvar(dlg.getProfessor());
                carregar(null);
            }
        });
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            int ok = JOptionPane.showConfirmDialog(this, "Excluir professor "+id+"?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (ok==JOptionPane.YES_OPTION){
                controller.excluir(id);
                carregar(null);
            }
        });

        carregar(null);
    }

    private void carregar(String termo){
        model.setRowCount(0);
        List<Professor> professores = (termo==null || termo.isBlank()) ? controller.listar() : controller.buscar(termo);
        for (Professor p : professores){
            model.addRow(new Object[]{p.getId(), p.getNomeCompleto(), p.getCpf(), p.getTelefone(), p.getEmail()});
        }
    }
}
