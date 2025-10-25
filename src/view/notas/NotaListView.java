package view.notas;

import controller.NotaController;
import model.entity.NotaFrequencia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NotaListView extends JPanel {
    private final NotaController controller = new NotaController();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","AlunoID","DisciplinaID","Período","Nota","Frequência(%)"}, 0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField txtBuscaAluno = new JTextField();

    public NotaListView(){
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.add(new JLabel("Aluno ID:"));
        txtBuscaAluno.setColumns(8);
        left.add(txtBuscaAluno);
        JButton btnFiltrar = new JButton("Filtrar");
        left.add(btnFiltrar);
        top.add(left, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        right.add(btnNovo); right.add(btnEditar); right.add(btnExcluir);
        top.add(right, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnFiltrar.addActionListener(e -> carregarPorAluno());
        btnNovo.addActionListener(e -> {
            NotaFormDialog dlg = new NotaFormDialog(null);
            dlg.setVisible(true);
            if (dlg.getNota()!=null){
                controller.salvar(dlg.getNota());
                carregar();
            }
        });
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row==-1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            NotaFrequencia n = controller.listar().stream().filter(x->x.getId()==id).findFirst().orElse(null);
            if (n==null) return;
            NotaFormDialog dlg = new NotaFormDialog(n);
            dlg.setVisible(true);
            if (dlg.getNota()!=null){
                controller.salvar(dlg.getNota());
                carregar();
            }
        });
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row==-1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            int ok = JOptionPane.showConfirmDialog(this, "Excluir registro "+id+"?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (ok==JOptionPane.YES_OPTION){
                controller.excluir(id);
                carregar();
            }
        });

        carregar();
    }

    private void carregar(){
        model.setRowCount(0);
        List<NotaFrequencia> list = controller.listar();
        for (NotaFrequencia n : list){
            model.addRow(new Object[]{n.getId(), n.getAlunoId(), n.getDisciplinaId(), n.getPeriodo(), n.getNota(), n.getFrequenciaPercent()});
        }
    }

    private void carregarPorAluno(){
        String txt = txtBuscaAluno.getText().trim();
        if (txt.isBlank()){ carregar(); return; }
        try{
            long alunoId = Long.parseLong(txt);
            model.setRowCount(0);
            List<NotaFrequencia> list = controller.porAluno(alunoId);
            for (NotaFrequencia n : list){
                model.addRow(new Object[]{n.getId(), n.getAlunoId(), n.getDisciplinaId(), n.getPeriodo(), n.getNota(), n.getFrequenciaPercent()});
            }
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Aluno ID inválido.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }
}
