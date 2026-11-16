package br.com.obras.bean;

import br.com.obras.dao.ObraDAO;
import br.com.obras.model.Obra;
import br.com.obras.model.StatusObra;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PainelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Obra> obras = new ArrayList<>();

    @PostConstruct
    public void init() {
        obras = new ObraDAO().listarTodos();
    }

    private long contar(StatusObra status) {
        return obras.stream().filter(o -> o.getStatus() == status).count();
    }

    public long getTotal() {
        return obras.size();
    }

    public long getPlanejadas() {
        return contar(StatusObra.EM_PLANEJAMENTO);
    }

    public long getEmAndamento() {
        return contar(StatusObra.EM_ANDAMENTO);
    }

    public long getConcluidas() {
        return contar(StatusObra.CONCLUIDA);
    }

    public long getCanceladas() {
        return contar(StatusObra.CANCELADA);
    }

    public List<Obra> getObras() {
        return obras;
    }
}