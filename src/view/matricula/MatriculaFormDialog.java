package view.matricula;

import model.entity.Matricula;

import javax.swing.*;
import java.awt.*;

public class MatriculaFormDialog extends JDialog {
    private Matricula matricula;

    private final JTextField txtAlunoId = new JTextField();
    private final JTextField txtTurmaId = new JTextField();

    public MatriculaFormDialog(Matricula m){
        setModal(true);
        setTitle(m==null ? "Nova Matrícula" : "Editar Matrícula");
        setSize(320, 180);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Aluno ID:")); form.add(txtAlunoId);
        form.add(new JLabel("Turma ID:")); form.add(txtTurmaId);
        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        actions.add(btnSalvar); actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);

        if (m!=null){
            this.matricula = m;
            txtAlunoId.setText(String.valueOf(m.getAlunoId()));
            txtTurmaId.setText(String.valueOf(m.getTurmaId()));
        } else this.matricula = new Matricula();

        btnSalvar.addActionListener(e -> {
            try{
                matricula.setAlunoId(Long.parseLong(txtAlunoId.getText().trim()));
                matricula.setTurmaId(Long.parseLong(txtTurmaId.getText().trim()));
                dispose();
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "IDs inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(e -> { matricula = null; dispose(); });
    }

    public Matricula getMatricula(){ return matricula; }
}
