package view.professor;

import controller.DisciplinaController;
import model.entity.Disciplina;
import model.entity.Professor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GerenciarDisciplinasDialog extends JDialog {
    private final DisciplinaController discController = new DisciplinaController();
    private final Professor professor;
    private final Map<Long, JCheckBox> checkboxes = new HashMap<>();
    private final List<Disciplina> todasDisciplinas; 

    public GerenciarDisciplinasDialog(Professor p) {
        this.professor = p;
        this.todasDisciplinas = discController.listar();
        
        setModal(true);
        setTitle("Disciplinas de " + p.getNomeCompleto());
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (Disciplina d : todasDisciplinas) {
            String label = String.format("%s (%s) - %s", d.getNome(), d.getCodigo(), d.getDescricao());
            JCheckBox cb = new JCheckBox(label);
            
            if (p.getDisciplinasIds() != null && p.getDisciplinasIds().contains(d.getId())) {
                cb.setSelected(true);
            }
            
            checkboxes.put(d.getId(), cb);
            listPanel.add(cb);
        }

        add(new JScrollPane(listPanel), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar Atribuições");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> salvarAtribuicoes());
        btnCancelar.addActionListener(e -> dispose());
        
        actions.add(btnSalvar);
        actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);
    }

    private void salvarAtribuicoes() {
        List<Long> novasIds = new ArrayList<>();
        
        for (Map.Entry<Long, JCheckBox> entry : checkboxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                novasIds.add(entry.getKey());
            }
        }
        
        professor.setDisciplinasIds(novasIds);
        JOptionPane.showMessageDialog(this, 
            "Disciplinas atribuídas com sucesso! Lembre-se de salvar o Professor.", 
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
