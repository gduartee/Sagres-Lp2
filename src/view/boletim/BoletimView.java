package view.boletim;

import controller.NotaController;
import model.entity.NotaFrequencia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BoletimView extends JPanel {
    private final NotaController controller = new NotaController();
    private final JTextField txtAlunoId = new JTextField();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"DisciplinaID","Período","Nota","Frequência(%)"}, 0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JLabel lblMedia = new JLabel("Média: 0.0");

    public BoletimView(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Aluno ID:"));
        txtAlunoId.setColumns(10);
        JButton btnCarregar = new JButton("Carregar");
        top.add(txtAlunoId);
        top.add(btnCarregar);
        top.add(lblMedia);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnCarregar.addActionListener(e -> carregar());
    }

    private void carregar(){
        model.setRowCount(0);
        try{
            long alunoId = Long.parseLong(txtAlunoId.getText().trim());
            List<NotaFrequencia> lst = controller.porAluno(alunoId);
            for(NotaFrequencia n: lst){
                model.addRow(new Object[]{n.getDisciplinaId(), n.getPeriodo(), n.getNota(), n.getFrequenciaPercent()});
            }
            lblMedia.setText(String.format("Média: %.2f", controller.mediaGeralAluno(alunoId)));
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Informe um ID válido de aluno.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }
}
