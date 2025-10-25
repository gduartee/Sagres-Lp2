package view;

import javax.swing.*;
import java.awt.*;
import model.entity.Role;
import model.entity.User;
import model.service.Session;
import view.aluno.AlunoListView;
import view.boletim.BoletimView;
import view.disciplina.DisciplinaListView;
import view.matricula.MatriculaListView;
import view.notas.NotaListView;
import view.professor.ProfessorListView;
import view.turma.TurmaListView;
import view.auth.LoginDialog;

public class MainFrame extends JFrame {

    private boolean ready = false;

    // Guardar itens de menu pra habilitar/desabilitar por cargo
    private JMenu cad;
    private JMenu acad;
    private JMenuItem miAlunos, miProf, miDisc, miTurma;
    private JMenuItem miMat, miNotas, miBoletim;
    private JMenuItem miLogout;

    public MainFrame(){
        setTitle("Sistema de Gestão de Colégio - MVC/JSON");
        setSize(980, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ==== LOGIN ====
        LoginDialog login = new LoginDialog(this);
        login.setVisible(true);
        User u = login.getUser();
        if (u == null) return; // cancelou
        setTitle(getTitle() + " | " + u.getNomeCompleto() + " (" + u.getRole() + ")");
        this.ready = true;

        // ==== MENU ====
        JMenuBar bar = new JMenuBar();

        cad = new JMenu("Cadastros");
        miAlunos = new JMenuItem("Alunos");
        miProf = new JMenuItem("Professores");
        miDisc = new JMenuItem("Disciplinas");
        miTurma = new JMenuItem("Turmas");
        cad.add(miAlunos); cad.add(miProf); cad.add(miDisc); cad.add(miTurma);

        acad = new JMenu("Acadêmico");
        miMat = new JMenuItem("Matrículas");
        miNotas = new JMenuItem("Notas / Frequência");
        miBoletim = new JMenuItem("Boletim");
        acad.add(miMat); acad.add(miNotas); acad.add(miBoletim);

        JMenu sistema = new JMenu("Sistema");
        miLogout = new JMenuItem("Sair");
        sistema.add(miLogout);

        bar.add(cad);
        bar.add(acad);
        bar.add(sistema);
        setJMenuBar(bar);

        JPanel content = new JPanel(new CardLayout());
        add(content, BorderLayout.CENTER);

        // Telas
        AlunoListView vAlunos = new AlunoListView();
        ProfessorListView vProfs = new ProfessorListView();
        DisciplinaListView vDisc = new DisciplinaListView();
        TurmaListView vTurma = new TurmaListView();
        MatriculaListView vMat = new MatriculaListView();
        NotaListView vNota = new NotaListView();
        BoletimView vBol = new BoletimView();

        content.add(vAlunos, "ALUNOS");
        content.add(vProfs, "PROF");
        content.add(vDisc, "DISC");
        content.add(vTurma, "TURMA");
        content.add(vMat, "MAT");
        content.add(vNota, "NOTA");
        content.add(vBol, "BOL");

        miAlunos.addActionListener(e -> ((CardLayout)content.getLayout()).show(content, "ALUNOS"));
        miProf.addActionListener(e -> ((CardLayout)content.getLayout()).show(content, "PROF"));
        miDisc.addActionListener(e -> ((CardLayout)content.getLayout()).show(content, "DISC"));
        miTurma.addActionListener(e -> ((CardLayout)content.getLayout()).show(content, "TURMA"));
        miMat.addActionListener(e -> ((CardLayout)content.getLayout()).show(content, "MAT"));
        miNotas.addActionListener(e -> ((CardLayout)content.getLayout()).show(content, "NOTA"));
        miBoletim.addActionListener(e -> ((CardLayout)content.getLayout()).show(content, "BOL"));

        miLogout.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(this, "Sair da sessão?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (ok==JOptionPane.YES_OPTION){
                dispose();
                System.exit(0);
            }
        });

        // ==== PERMISSÕES POR CARGO ====
        applyRolePermissions(u.getRole());
    }

    private void applyRolePermissions(Role role){
        // Regras simples:
        // ADMIN: tudo habilitado
        // PROFESSOR: pode Notas/Frequência e Boletim; vê Alunos (consulta) mas não cadastros pesados; sem Matrícula/Professor/Disciplina/Turma
        // ALUNO: apenas Boletim
        boolean isAdmin = (role==Role.ADMIN);
        boolean isProf  = (role==Role.PROFESSOR);
        boolean isAluno = (role==Role.ALUNO);

        // Menu Cadastros
        cad.setEnabled(isAdmin);
        miAlunos.setEnabled(isAdmin);   // se quiser liberar leitura p/ professor, deixe habilitado e bloqueie botões nas views
        miProf.setEnabled(isAdmin);
        miDisc.setEnabled(isAdmin);
        miTurma.setEnabled(isAdmin);

        // Menu Acadêmico
        miMat.setEnabled(isAdmin);                 // só admin
        miNotas.setEnabled(isAdmin || isProf);     // prof e admin
        miBoletim.setEnabled(true);                // todos

        // Opcional: avisar quando clicarem em algo bloqueado (já fica desabilitado)
    }

    public boolean isReady(){ return ready; }
}
