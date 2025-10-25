package view.notas;

import model.entity.NotaFrequencia;

import javax.swing.*;
import java.awt.*;

public class NotaFormDialog extends JDialog {
    private NotaFrequencia nota;

    private final JTextField txtAlunoId = new JTextField();
    private final JTextField txtDisciplinaId = new JTextField();
    private final JTextField txtPeriodo = new JTextField();
    private final JTextField txtNota = new JTextField();
    private final JTextField txtFreq = new JTextField();

    public NotaFormDialog(NotaFrequencia n){
        setModal(true);
        setTitle(n==null ? "Nova Nota/Frequência" : "Editar Nota/Frequência");
        setSize(380, 240);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Aluno ID:")); form.add(txtAlunoId);
        form.add(new JLabel("Disciplina ID:")); form.add(txtDisciplinaId);
        form.add(new JLabel("Período:")); form.add(txtPeriodo);
        form.add(new JLabel("Nota (0..10):")); form.add(txtNota);
        form.add(new JLabel("Frequência (%):")); form.add(txtFreq);
        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        actions.add(btnSalvar); actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);

        if (n!=null){
            this.nota = n;
            txtAlunoId.setText(String.valueOf(n.getAlunoId()));
            txtDisciplinaId.setText(String.valueOf(n.getDisciplinaId()));
            txtPeriodo.setText(n.getPeriodo());
            txtNota.setText(String.valueOf(n.getNota()));
            txtFreq.setText(String.valueOf(n.getFrequenciaPercent()));
        } else this.nota = new NotaFrequencia();

        btnSalvar.addActionListener(e -> {
            try{
                nota.setAlunoId(Long.parseLong(txtAlunoId.getText().trim()));
                nota.setDisciplinaId(Long.parseLong(txtDisciplinaId.getText().trim()));
                nota.setPeriodo(txtPeriodo.getText().trim());
                nota.setNota(Double.parseDouble(txtNota.getText().trim()));
                nota.setFrequenciaPercent(Integer.parseInt(txtFreq.getText().trim()));
                dispose();
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Valores numéricos inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(e -> { nota = null; dispose(); });
    }

    public NotaFrequencia getNota(){ return nota; }
}
