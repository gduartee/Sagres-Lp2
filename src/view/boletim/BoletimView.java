
package view.boletim;

import controller.NotaController;
import controller.BoletimController;
import model.entity.NotaFrequencia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JEditorPane;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class BoletimView extends JPanel {
    private final NotaController controller = new NotaController();
    private final BoletimController boletimController = new BoletimController();
    private final JTextField txtAlunoId = new JTextField(8);
    private final JTextField txtPeriodo = new JTextField(8);
    private final JComboBox<String> cmbLayout = new JComboBox<>(new String[]{"Compacto","Detalhado"});
    private final JButton btnGerar = new JButton("Gerar");
    private final JButton btnPublicar = new JButton("Publicar");
    private final JButton btnVisualizar = new JButton("Visualizar/Imprimir");

    private final JLabel lblMedia = new JLabel("Média: 0.00");
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"DisciplinaID","Período","Nota","Frequência(%)"}, 0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);

    public BoletimView(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Aluno ID:")); top.add(txtAlunoId);
        top.add(new JLabel("Período:")); top.add(txtPeriodo);
        top.add(new JLabel("Layout:")); top.add(cmbLayout);
        top.add(btnGerar); top.add(btnPublicar); top.add(btnVisualizar);
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(lblMedia);
        add(bottom, BorderLayout.SOUTH);

        btnGerar.addActionListener(e -> carregar());
        btnPublicar.addActionListener(e -> publicar());
        btnVisualizar.addActionListener(e -> visualizar());
    }

    private void carregar(){
        model.setRowCount(0);
        try{
            long alunoId = Long.parseLong(txtAlunoId.getText().trim());
            List<NotaFrequencia> lst = controller.porAluno(alunoId);
            for(NotaFrequencia n: lst){
                model.addRow(new Object[]{n.getDisciplinaId(), n.getPeriodo(), n.getNota(), n.getFrequenciaPercent()});
            }
            lblMedia.setText(String.format("Média: %.2f", controller.mediaGeralAluno(alunoId)));
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Informe um ID válido de aluno.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void publicar(){
        try{
            long alunoId = Long.parseLong(txtAlunoId.getText().trim());
            String periodo = txtPeriodo.getText().trim();
            String layout = (String)cmbLayout.getSelectedItem();
            File f = boletimController.gerarBoletim(alunoId, periodo, layout);
            // copy to published folder
            File pubDir = new File("data/boletins/published");
            if(!pubDir.exists()) pubDir.mkdirs();
            File dest = new File(pubDir, f.getName());
            Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(this, "Boletim publicado com sucesso em: " + dest.getPath(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Informe um ID válido de aluno.", "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Erro ao publicar boletim: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void visualizar(){
        try{
            long alunoId = Long.parseLong(txtAlunoId.getText().trim());
            String periodo = txtPeriodo.getText().trim();
            String layout = (String)cmbLayout.getSelectedItem();
            File f = boletimController.gerarBoletim(alunoId, periodo, layout);
            String html = java.nio.file.Files.readString(f.toPath());
            JEditorPane editor = new JEditorPane("text/html", html);
            editor.setEditable(false);
            JScrollPane sp = new JScrollPane(editor);
            JDialog d = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Visualizar Boletim", true);
            d.setSize(700,500);
            d.setLayout(new BorderLayout());
            d.add(sp, BorderLayout.CENTER);
            JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnPrint = new JButton("Imprimir");
            btnPrint.addActionListener(ev -> {
                try{
                    boolean ok = editor.print();
                    if(ok) JOptionPane.showMessageDialog(d, "Impressão enviada.", "Imprimir", JOptionPane.INFORMATION_MESSAGE);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(d, "Erro de impressão: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
            p.add(btnPrint);
            d.add(p, BorderLayout.SOUTH);
            d.setLocationRelativeTo(this);
            d.setVisible(true);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Informe um ID válido de aluno.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Erro ao visualizar boletim: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
