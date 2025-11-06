package view.user;

import model.entity.Role;
import model.entity.User;
import model.service.UserService;

import javax.swing.*;
import java.awt.*;

public class UserFormDialog extends JDialog {
	private UserService service = new UserService();
	
    private User user;
    private final JTextField txtUsername = new JTextField();
    private final JTextField txtNome = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JComboBox<Role> cbxCargo = new JComboBox<Role>(Role.values());
    private final JTextField txtSenha = new JTextField();

    public UserFormDialog(User u){
        setModal(true);
        setTitle(u==null ? "Novo User" : "Editar User");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Nome de Usuário:")); form.add(txtUsername);
        form.add(new JLabel("Nome Completo:")); form.add(txtNome);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Cargo:")); form.add(cbxCargo);
        form.add(new JLabel("Senha:")); form.add(txtSenha);
        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        actions.add(btnSalvar); actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);

        if (u != null){
            this.user = u;
            txtUsername.setText(u.getUsername());
            txtNome.setText(u.getNomeCompleto());
            txtEmail.setText(u.getEmail());
            cbxCargo.setSelectedItem(u.getRole());
            txtSenha.setText(u.getPasswordHash());
        } else this.user = new User();

        btnSalvar.addActionListener(e -> {
        	try
        	{
        		this.user = service.salvarNovo(txtUsername.getText().trim(), txtNome.getText().trim(), txtEmail.getText().trim(), cbxCargo.getItemAt(cbxCargo.getSelectedIndex()), txtSenha.getText().trim());
        		
        		dispose();
        	} catch(IllegalArgumentException ex)
        	{
        		JOptionPane.showMessageDialog(this, "Algum campo obrigatório vazio ou dados já cadastrados.", "Erro", JOptionPane.ERROR_MESSAGE);
        	}
        });
        btnCancelar.addActionListener(e -> { user = null; dispose(); });
    }

    public User getUser(){ return user; }
}
