package view.disciplina;

import model.entity.Disciplina;
import javax.swing.*;
import java.awt.*;

public class DisciplinaFormDialog extends JDialog {
    private Disciplina disciplina;
    private final JTextField txtNome = new JTextField();
    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtCarga = new JTextField();
    private final JTextArea txtDesc = new JTextArea(3,20);

    public DisciplinaFormDialog(Disciplina d){
        setModal(true);
        setTitle(d==null ? "Nova Disciplina" : "Editar Disciplina");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Nome:")); form.add(txtNome);
        form.add(new JLabel("Código:")); form.add(txtCodigo);
        form.add(new JLabel("Carga Horária:")); form.add(txtCarga);
        form.add(new JLabel("Descrição:"));
        form.add(new JScrollPane(txtDesc));
        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        actions.add(btnSalvar); actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);

        if (d != null){
            this.disciplina = d;
            txtNome.setText(d.getNome());
            txtCodigo.setText(d.getCodigo());
            txtCarga.setText(String.valueOf(d.getCargaHoraria()));
            txtDesc.setText(d.getDescricao());
        } else this.disciplina = new Disciplina();

        btnSalvar.addActionListener(e -> {
            disciplina.setNome(txtNome.getText().trim());
            disciplina.setCodigo(txtCodigo.getText().trim());
            disciplina.setCargaHoraria(Integer.parseInt(txtCarga.getText().trim()));
            disciplina.setDescricao(txtDesc.getText().trim());
            dispose();
        });
        btnCancelar.addActionListener(e -> { disciplina = null; dispose(); });
    }

    public Disciplina getDisciplina(){ return disciplina; }
}
