package view.professor;

import model.entity.Professor;

import javax.swing.*;
import java.awt.*;

public class ProfessorFormDialog extends JDialog {
    private Professor professor;
    private final JTextField txtNome = new JTextField();
    private final JTextField txtCpf = new JTextField();
    private final JTextField txtRg = new JTextField();
    private final JTextField txtDataNasc = new JTextField();
    private final JTextField txtEndereco = new JTextField();
    private final JTextField txtTelefone = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextField txtFormacao = new JTextField();

    public ProfessorFormDialog(Professor p){
        setModal(true);
        setTitle(p==null ? "Novo Professor" : "Editar Professor");
        setSize(480, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Nome Completo:")); form.add(txtNome);
        form.add(new JLabel("CPF:")); form.add(txtCpf);
        form.add(new JLabel("RG:")); form.add(txtRg);
        form.add(new JLabel("Data Nascimento:")); form.add(txtDataNasc);
        form.add(new JLabel("Endereço:")); form.add(txtEndereco);
        form.add(new JLabel("Telefone:")); form.add(txtTelefone);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Formação Acadêmica:")); form.add(txtFormacao);
        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        actions.add(btnSalvar); actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);

        if (p != null){
            this.professor = p;
            txtNome.setText(p.getNomeCompleto());
            txtCpf.setText(p.getCpf());
            txtRg.setText(p.getRg());
            txtDataNasc.setText(p.getDataNascimento());
            txtEndereco.setText(p.getEndereco());
            txtTelefone.setText(p.getTelefone());
            txtEmail.setText(p.getEmail());
            txtFormacao.setText(p.getFormacaoAcademica());
        } else this.professor = new Professor();

        btnSalvar.addActionListener(e -> {
            professor.setNomeCompleto(txtNome.getText().trim());
            professor.setCpf(txtCpf.getText().trim());
            professor.setRg(txtRg.getText().trim());
            professor.setDataNascimento(txtDataNasc.getText().trim());
            professor.setEndereco(txtEndereco.getText().trim());
            professor.setTelefone(txtTelefone.getText().trim());
            professor.setEmail(txtEmail.getText().trim());
            professor.setFormacaoAcademica(txtFormacao.getText().trim());
            dispose();
        });
        btnCancelar.addActionListener(e -> { professor = null; dispose(); });
    }

    public Professor getProfessor(){ return professor; }
}
