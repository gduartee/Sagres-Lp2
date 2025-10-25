package view.aluno;

import controller.AlunoController;
import model.entity.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AlunoListView extends JPanel {
    private final AlunoController controller = new AlunoController();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Nome","CPF","Telefone","Email"}, 0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField txtBusca = new JTextField();

    public AlunoListView(){
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
            AlunoFormDialog dlg = new AlunoFormDialog(null);
            dlg.setVisible(true);
            if (dlg.getAluno() != null){
                controller.salvar(dlg.getAluno());
                carregar(null);
            }
        });
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            Aluno a = controller.listar().stream().filter(x->x.getId()==id).findFirst().orElse(null);
            if(a==null) return;
            AlunoFormDialog dlg = new AlunoFormDialog(a);
            dlg.setVisible(true);
            if (dlg.getAluno() != null){
                controller.salvar(dlg.getAluno());
                carregar(null);
            }
        });
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            int ok = JOptionPane.showConfirmDialog(this, "Excluir aluno "+id+"?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (ok==JOptionPane.YES_OPTION){
                controller.excluir(id);
                carregar(null);
            }
        });

        carregar(null);
    }

    private void carregar(String termo){
        model.setRowCount(0);
        List<Aluno> alunos = (termo==null || termo.isBlank()) ? controller.listar() : controller.buscar(termo);
        for (Aluno a : alunos){
            model.addRow(new Object[]{a.getId(), a.getNomeCompleto(), a.getCpf(), a.getTelefone(), a.getEmail()});
        }
    }
}
