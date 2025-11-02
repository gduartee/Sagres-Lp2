
package model.service;

import model.entity.NotaFrequencia;
import model.entity.Disciplina;
import model.repo.DisciplinaRepository;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BoletimService {
    private final NotaService notaService = new NotaService();
    private final DisciplinaRepository disciplinaRepo = new DisciplinaRepository();

    public File gerarBoletim(long alunoId, String periodo, String layout) throws IOException {
        List<NotaFrequencia> notas = notaService.listarPorAluno(alunoId).stream()
                .filter(n -> periodo==null || periodo.isBlank() || periodo.equals(n.getPeriodo()))
                .collect(Collectors.toList());

        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset='utf-8'><title>Boletim</title></head><body>");
        html.append("<h2>Boletim - Aluno ID: ").append(alunoId).append("</h2>");
        html.append("<p>Período: ").append(periodo==null || periodo.isBlank() ? "Todos" : periodo).append("</p>");
        if("Detalhado".equals(layout)){
            html.append("<table border='1' cellpadding='4'><tr><th>Disciplina</th><th>Período</th><th>Nota</th><th>Frequência</th></tr>");
            for(NotaFrequencia n : notas){
                Disciplina d = disciplinaRepo.findById(n.getDisciplinaId());
                String nome = d==null?("ID "+n.getDisciplinaId()):d.getNome();
                html.append("<tr><td>").append(nome).append("</td><td>").append(n.getPeriodo())
                    .append("</td><td>").append(n.getNota()).append("</td><td>").append(n.getFrequenciaPercent()).append("%</td></tr>");
            }
            html.append("</table>");
        } else {
            html.append("<table border='1' cellpadding='4'><tr><th>Disciplina</th><th>Nota</th></tr>");
            for(NotaFrequencia n : notas){
                Disciplina d = disciplinaRepo.findById(n.getDisciplinaId());
                String nome = d==null?("ID "+n.getDisciplinaId()):d.getNome();
                html.append("<tr><td>").append(nome).append("</td><td>").append(n.getNota()).append("</td></tr>");
            }
            html.append("</table>");
        }
        double media = notaService.mediaGeralAluno(alunoId);
        html.append("<p>Média geral: ").append(String.format("%.2f", media)).append("</p>");
        html.append("</body></html>");

        File dir = new File("data/boletins");
        if(!dir.exists()) dir.mkdirs();
        String safePeriodo = (periodo==null || periodo.isBlank()) ? "todos" : periodo.replaceAll("\\s+","_");
        String filename = String.format("data/boletins/boletim_aluno_%d_%s.html", alunoId, safePeriodo);
        File out = new File(filename);
        try(FileWriter fw = new FileWriter(out)){
            fw.write(html.toString());
        }
        return out;
    }
}
