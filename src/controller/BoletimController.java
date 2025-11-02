
package controller;

import model.service.BoletimService;
import java.io.File;

public class BoletimController {
    private final BoletimService service = new BoletimService();
    public File gerarBoletim(long alunoId, String periodo, String layout) throws Exception {
        return service.gerarBoletim(alunoId, periodo, layout);
    }
}
