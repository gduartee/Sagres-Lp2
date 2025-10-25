package view.auth;

import controller.AuthController;
import model.entity.User;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private final JTextField txtUser = new JTextField();
    private final JPasswordField txtPass = new JPasswordField();
    private User user;

    public LoginDialog(Frame owner){
        super(owner, "Login", true);
        setSize(360, 180);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Usuário:")); form.add(txtUser);
        form.add(new JLabel("Senha:")); form.add(txtPass);
        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnOk = new JButton("Entrar");
        JButton btnCancel = new JButton("Cancelar");
        actions.add(btnOk); actions.add(btnCancel);
        add(actions, BorderLayout.SOUTH);

        AuthController controller = new AuthController();

        btnOk.addActionListener(e -> {
            try{
                String u = txtUser.getText().trim();
                String p = new String(txtPass.getPassword());
                this.user = controller.login(u, p);
                dispose();
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de login", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancel.addActionListener(e -> { this.user = null; dispose(); });
    }

    public User getUser(){ return user; }
}
