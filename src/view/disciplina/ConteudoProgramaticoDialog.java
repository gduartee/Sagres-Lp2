package view.disciplina;

import model.entity.Disciplina;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConteudoProgramaticoDialog extends JDialog {
	
    public ConteudoProgramaticoDialog(Disciplina d) {
        setModal(true);
        setTitle("Conteúdo Programático: " + d.getNome());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea txtConteudo = new JTextArea();
        txtConteudo.setEditable(false);
        txtConteudo.setLineWrap(true);
        txtConteudo.setWrapStyleWord(true);

        if (d.getConteudoProgramatico() != null && !d.getConteudoProgramatico().isEmpty()) {
            String conteudoText = String.join("\n", d.getConteudoProgramatico());
            txtConteudo.setText(conteudoText);
        } else {
            txtConteudo.setText("Nenhum conteúdo programático registrado.");
        }

        add(new JScrollPane(txtConteudo), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        actions.add(btnFechar);
        add(actions, BorderLayout.SOUTH);
    }
}
