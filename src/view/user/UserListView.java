package view.user;

import controller.UserController;
import model.entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserListView extends JPanel
{
    private final UserController controller = new UserController();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Nome de Usuário","Nome","E-mail","Cargo","Senha"}, 0){
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField txtBusca = new JTextField();

    public UserListView(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Buscar (nome de usuário/nome/e-mail): "), BorderLayout.WEST);
        top.add(txtBusca, BorderLayout.CENTER);
        
        JButton btnBuscar = new JButton("Buscar");
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        
        JPanel buttons = new JPanel();
        buttons.add(btnBuscar); buttons.add(btnNovo); buttons.add(btnEditar); buttons.add(btnExcluir);
        
        top.add(buttons, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> carregar(txtBusca.getText().trim()));
        btnNovo.addActionListener(e -> {
            UserFormDialog dlg = new UserFormDialog(null);
            dlg.setVisible(true);
            if (dlg.getUser() != null){
                controller.salvar(dlg.getUser());
                carregar(null);
            }
        });
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            User u = controller.listar().stream().filter(x->x.getId()==id).findFirst().orElse(null);
            if(u==null) return;
            UserFormDialog dlg = new UserFormDialog(u);
            dlg.setVisible(true);
            if (dlg.getUser() != null){
                controller.salvar(dlg.getUser());
                carregar(null);
            }
        });
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            long id = Long.parseLong(model.getValueAt(row,0).toString());
            int ok = JOptionPane.showConfirmDialog(this, "Excluir usuário "+id+"?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (ok==JOptionPane.YES_OPTION){
                controller.excluir(id);
                carregar(null);
            }
        });

        carregar(null);
    }

    private void carregar(String termo){
        model.setRowCount(0);
        List<User> lista = (termo==null || termo.isBlank()) ? controller.listar() : controller.buscar(termo);
        for (User u : lista){
            model.addRow(new Object[]{u.getId(), u.getUsername(), u.getNomeCompleto(), u.getEmail(), u.getRole(), u.getPasswordHash()});
        }
    }
}
