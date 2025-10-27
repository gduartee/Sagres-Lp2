package view.turma;

import model.entity.Turma;
import controller.PeriodoLetivoController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TurmaFormDialog extends JDialog {
    private Turma turma;
    private final PeriodoLetivoController periodoController = new PeriodoLetivoController();
    
    private final JTextField txtSerie = new JTextField();
    private final JTextField txtNomeTurma = new JTextField();
    private final JComboBox<String> cmbAno = new JComboBox<>();

    public TurmaFormDialog(Turma t){
        setModal(true);
        setTitle(t==null ? "Nova Turma" : "Editar Turma");
        setSize(360, 220);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        carregarAnosEscolares();

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Ano Escolar:")); form.add(cmbAno);
        form.add(new JLabel("Série (ex: 3º Ano):")); form.add(txtSerie);
        form.add(new JLabel("Turma (ex: A):")); form.add(txtNomeTurma);
        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        actions.add(btnSalvar); actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);

        if (t!=null){
            this.turma = t;
            txtSerie.setText(t.getSerie());
            txtNomeTurma.setText(t.getNomeTurma());
            cmbAno.setSelectedItem(t.getAnoEscolar());
            
            if (cmbAno.getSelectedItem() == null && t.getAnoEscolar() != null) {
                JOptionPane.showMessageDialog(this, "O Ano Escolar '"+t.getAnoEscolar()+"' não está mais configurado nos Períodos Letivos. Escolha um ano válido.", "Aviso de Data", JOptionPane.WARNING_MESSAGE);
            }
        } else this.turma = new Turma();

        btnSalvar.addActionListener(e -> {
        	try {
                if (cmbAno.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um Ano Escolar configurado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                turma.setNomeTurma(txtNomeTurma.getText().trim());
                turma.setSerie(txtSerie.getText().trim());
                turma.setAnoEscolar((String) cmbAno.getSelectedItem()); 
                
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(e -> { turma = null; dispose(); });
    }
    
    private void carregarAnosEscolares() {
        List<String> anos = periodoController.listarAnosEscolares();
        cmbAno.removeAllItems();
        if (anos.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Nenhum Ano Escolar configurado. Configure em 'Acadêmico -> Períodos Letivos'.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            for (String ano : anos) {
                cmbAno.addItem(ano);
            }
        }
    }

    public Turma getTurma(){ return turma; }
}
