package view.periodo;

import model.entity.PeriodoLetivo;
import model.entity.TipoPeriodo;

import javax.swing.*;
import java.awt.*;

public class PeriodoLetivoFormDialog extends JDialog {
    private PeriodoLetivo periodo;
    private final JTextField txtAno = new JTextField();
    private final JTextField txtNome = new JTextField();
    private final JComboBox<TipoPeriodo> cmbTipo = new JComboBox<>(TipoPeriodo.values());

    public PeriodoLetivoFormDialog(PeriodoLetivo p){
        setModal(true);
        setTitle(p==null ? "Novo Período Letivo" : "Editar Período Letivo");
        setSize(380, 220);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Ano Escolar:")); form.add(txtAno);
        form.add(new JLabel("Nome:")); form.add(txtNome);
        form.add(new JLabel("Tipo:")); form.add(cmbTipo);
        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        actions.add(btnSalvar); actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);

        if (p!=null){
            this.periodo = p;
            txtAno.setText(p.getAnoEscolar());
            txtNome.setText(p.getNome());
            cmbTipo.setSelectedItem(p.getTipo());
        } else this.periodo = new PeriodoLetivo();

        btnSalvar.addActionListener(e -> {
            try {
                periodo.setAnoEscolar(txtAno.getText().trim());
                periodo.setNome(txtNome.getText().trim());
                periodo.setTipo((TipoPeriodo) cmbTipo.getSelectedItem());
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(e -> { periodo = null; dispose(); });
    }

    public PeriodoLetivo getPeriodo(){ return periodo; }
}
