package view.disciplina;

import model.entity.Disciplina;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DisciplinaFormDialog extends JDialog {
    private Disciplina disciplina;
    private final JTextField txtNome = new JTextField();
    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtCarga = new JTextField();
    private final JTextArea txtDesc = new JTextArea(3,20);
    private final JTextArea txtConteudo = new JTextArea(5, 20);

    public DisciplinaFormDialog(Disciplina d){
        setModal(true);
        setTitle(d==null ? "Nova Disciplina" : "Editar Disciplina");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Nome:")); form.add(txtNome);
        form.add(new JLabel("Código:")); form.add(txtCodigo);
        form.add(new JLabel("Carga Horária:")); form.add(txtCarga);
        form.add(new JLabel("Descrição:"));
        form.add(new JScrollPane(txtDesc));
        form.add(new JLabel("<html><b>Conteúdo Programático:</b><br>(Um tópico por linha)</html>"));
        form.add(new JScrollPane(txtConteudo));
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
            if (d.getConteudoProgramatico() != null) {
                String conteudoText = String.join("\n", d.getConteudoProgramatico());
                txtConteudo.setText(conteudoText);
            }
        } else this.disciplina = new Disciplina();

        btnSalvar.addActionListener(e -> {
        	try {
                disciplina.setNome(txtNome.getText().trim());
                disciplina.setCodigo(txtCodigo.getText().trim());
                disciplina.setCargaHoraria(Integer.parseInt(txtCarga.getText().trim()));
                disciplina.setDescricao(txtDesc.getText().trim());
                
                List<String> conteudoList = Arrays.stream(txtConteudo.getText().split("\n"))
                                                  .map(String::trim)
                                                  .filter(s -> !s.isBlank())
                                                  .collect(Collectors.toList());
                disciplina.setConteudoProgramatico(conteudoList);
                
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "A Carga Horária deve ser um número inteiro válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(e -> { disciplina = null; dispose(); });
    }

    public Disciplina getDisciplina(){ return disciplina; }
}
