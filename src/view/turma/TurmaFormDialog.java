package view.turma;

import model.entity.Turma;

import javax.swing.*;
import java.awt.*;

public class TurmaFormDialog extends JDialog {
    private Turma turma;

    private final JTextField txtAno = new JTextField();
    private final JTextField txtSerie = new JTextField();
    private final JTextField txtNomeTurma = new JTextField();

    public TurmaFormDialog(Turma t){
        setModal(true);
        setTitle(t==null ? "Nova Turma" : "Editar Turma");
        setSize(360, 220);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Ano Escolar:")); form.add(txtAno);
        form.add(new JLabel("Série:")); form.add(txtSerie);
        form.add(new JLabel("Turma:")); form.add(txtNomeTurma);
        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        actions.add(btnSalvar); actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);

        if (t!=null){
            this.turma = t;
            txtAno.setText(t.getAnoEscolar());
            txtSerie.setText(t.getSerie());
            txtNomeTurma.setText(t.getNomeTurma());
        } else this.turma = new Turma();

        btnSalvar.addActionListener(e -> {
            turma.setAnoEscolar(txtAno.getText().trim());
            turma.setSerie(txtSerie.getText().trim());
            turma.setNomeTurma(txtNomeTurma.getText().trim());
            dispose();
        });
        btnCancelar.addActionListener(e -> { turma = null; dispose(); });
    }

    public Turma getTurma(){ return turma; }
}
