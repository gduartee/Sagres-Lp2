package app;

import javax.swing.SwingUtilities;
import model.entity.User;
import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(); // MainFrame já chama o Login
            if (frame.isReady()){
                frame.setVisible(true);
            } else {
                // usuário cancelou login
                System.exit(0);
            }
        });
    }
}
