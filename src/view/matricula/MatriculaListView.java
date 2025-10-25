package view.matricula;

import controller.MatriculaController;
import model.entity.Matricula;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MatriculaListView extends JPanel {
    private final MatriculaController controller = new MatriculaController();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","AlunoID","TurmaID"}, 0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);

    public MatriculaListView(){
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnNovo = new JButton("Nova");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnTransferir = new JButton("Transferir");
        top.add(btnNovo); top.add(btnEditar); top.add(btnTransferir); top.add(btnExcluir);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnNovo.addActionListener(e -> {
            MatriculaFormDialog dlg = new MatriculaFormDialog(null);
            dlg.setVisible(true);
            if (dlg.getMatricula()!=null){
                controller.salvar(dlg.getMatricula());
                carregar();
            }
        });

        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row==-1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            Matricula m = controller.listar().stream().filter(x->x.getId()==id).findFirst().orElse(null);
            if (m==null) return;
            MatriculaFormDialog dlg = new MatriculaFormDialog(m);
            dlg.setVisible(true);
            if (dlg.getMatricula()!=null){
                controller.salvar(dlg.getMatricula());
                carregar();
            }
        });

        btnTransferir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row==-1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            String novaTurma = JOptionPane.showInputDialog(this, "Novo TurmaID:");
            if (novaTurma==null || novaTurma.isBlank()) return;
            try{
                long novaTurmaId = Long.parseLong(novaTurma.trim());
                controller.transferir(id, novaTurmaId);
                carregar();
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "TurmaID inválido.", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row==-1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            int ok = JOptionPane.showConfirmDialog(this, "Excluir matrícula "+id+"?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (ok==JOptionPane.YES_OPTION){
                controller.excluir(id);
                carregar();
            }
        });

        carregar();
    }

    private void carregar(){
        model.setRowCount(0);
        List<Matricula> list = controller.listar();
        for (Matricula m: list){
            model.addRow(new Object[]{m.getId(), m.getAlunoId(), m.getTurmaId()});
        }
    }
}
