package view.aluno;

import model.entity.Aluno;

import javax.swing.*;
import java.awt.*;

public class AlunoFormDialog extends JDialog {
    private Aluno aluno;

    private final JTextField txtNome = new JTextField();
    private final JTextField txtDataNasc = new JTextField();
    private final JTextField txtCpf = new JTextField();
    private final JTextField txtRg = new JTextField();
    private final JTextField txtEndereco = new JTextField();
    private final JTextField txtTelefone = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextField txtRespNome = new JTextField();
    private final JTextField txtRespTel = new JTextField();
    private final JTextField txtRespEmail = new JTextField();

    public AlunoFormDialog(Aluno a){
        setModal(true);
        setTitle(a==null ? "Novo Aluno" : "Editar Aluno");
        setSize(520, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Nome Completo:")); form.add(txtNome);
        form.add(new JLabel("Data Nasc (yyyy-MM-dd):")); form.add(txtDataNasc);
        form.add(new JLabel("CPF:")); form.add(txtCpf);
        form.add(new JLabel("RG:")); form.add(txtRg);
        form.add(new JLabel("Endereço:")); form.add(txtEndereco);
        form.add(new JLabel("Telefone:")); form.add(txtTelefone);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Resp. Nome:")); form.add(txtRespNome);
        form.add(new JLabel("Resp. Telefone:")); form.add(txtRespTel);
        form.add(new JLabel("Resp. Email:")); form.add(txtRespEmail);
        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        actions.add(btnSalvar); actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);

        if (a != null) {
            this.aluno = a;
            txtNome.setText(a.getNomeCompleto());
            txtDataNasc.setText(a.getDataNascimento());
            txtCpf.setText(a.getCpf());
            txtRg.setText(a.getRg());
            txtEndereco.setText(a.getEndereco());
            txtTelefone.setText(a.getTelefone());
            txtEmail.setText(a.getEmail());
            txtRespNome.setText(a.getResponsavelNome());
            txtRespTel.setText(a.getResponsavelTelefone());
            txtRespEmail.setText(a.getResponsavelEmail());
        } else {
            this.aluno = new Aluno();
        }

        btnSalvar.addActionListener(e -> {
            try{
                aluno.setNomeCompleto(txtNome.getText().trim());
                aluno.setDataNascimento(txtDataNasc.getText().trim());
                aluno.setCpf(txtCpf.getText().trim());
                aluno.setRg(txtRg.getText().trim());
                aluno.setEndereco(txtEndereco.getText().trim());
                aluno.setTelefone(txtTelefone.getText().trim());
                aluno.setEmail(txtEmail.getText().trim());
                aluno.setResponsavelNome(txtRespNome.getText().trim());
                aluno.setResponsavelTelefone(txtRespTel.getText().trim());
                aluno.setResponsavelEmail(txtRespEmail.getText().trim());
                dispose();
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(e -> { aluno = null; dispose(); });
    }

    public Aluno getAluno(){ return aluno; }
}
