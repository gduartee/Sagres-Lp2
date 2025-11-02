
package view.turma;

import model.entity.Turma;
import model.entity.Disciplina;
import controller.DisciplinaController;
import controller.TurmaController;
import model.service.TurmaService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AssociarDisciplinaView extends JDialog {
    private final TurmaService turmaService = new TurmaService();
    private final DisciplinaController disciplinaController = new DisciplinaController();
    private final JComboBox<Turma> cmbTurmas = new JComboBox<>();
    private final JList<Disciplina> lstDisciplinas = new JList<>();
    private final JButton btnSalvar = new JButton("Salvar Associações");

    public AssociarDisciplinaView(Frame parent){
        super(parent, "Associar Disciplinas a Turma", true);
        setSize(700,400);
        setLayout(new BorderLayout());
        loadTurmas();
        loadDisciplinas();
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Turma:")); top.add(cmbTurmas);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(lstDisciplinas), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(btnSalvar);
        add(bottom, BorderLayout.SOUTH);
        btnSalvar.addActionListener(e -> salvar());
        setLocationRelativeTo(parent);
    }

    private void loadTurmas(){
        List<Turma> turmas = turmaService.listar();
        DefaultComboBoxModel<Turma> m = new DefaultComboBoxModel<>();
        for(Turma t: turmas) m.addElement(t);
        cmbTurmas.setModel(m);
        cmbTurmas.setRenderer(new DefaultListCellRenderer(){
            public Component getListCellRendererComponent(JList<?> list,Object value,int index,boolean isSelected,boolean cellHasFocus){
                Turma t = (Turma) value;
                String s = t==null? "": t.getSerie() + " - " + t.getNomeTurma() + " (" + t.getAnoEscolar() + ")";
                return super.getListCellRendererComponent(list, s, index, isSelected, cellHasFocus);
            }
        });
    }

    private void loadDisciplinas(){
        List<Disciplina> disciplinas = disciplinaController.listar();
        DefaultListModel<Disciplina> m = new DefaultListModel<>();
        for(Disciplina d: disciplinas) m.addElement(d);
        lstDisciplinas.setModel(m);
        lstDisciplinas.setCellRenderer(new DefaultListCellRenderer(){
            public Component getListCellRendererComponent(JList<?> list,Object value,int index,boolean isSelected,boolean cellHasFocus){
                Disciplina d = (Disciplina) value;
                String s = d==null? "": d.getNome() + " (" + d.getCodigo() + ")";
                return super.getListCellRendererComponent(list, s, index, isSelected, cellHasFocus);
            }
        });
        lstDisciplinas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void salvar(){
        Turma t = (Turma) cmbTurmas.getSelectedItem();
        if(t==null){ JOptionPane.showMessageDialog(this,"Selecione uma turma.","Atenção",JOptionPane.WARNING_MESSAGE); return; }
        List<Disciplina> sel = lstDisciplinas.getSelectedValuesList();
        List<Long> ids = sel.stream().map(Disciplina::getId).collect(Collectors.toList());
        t.setDisciplinasIds(ids);
        try{
            turmaService.salvar(t);
            JOptionPane.showMessageDialog(this,"Associação salva com sucesso.","Sucesso",JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Erro ao salvar: "+ex.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
        }
    }
}
