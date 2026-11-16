package br.com.obras.bean;

import br.com.obras.dao.ObraDAO;
import br.com.obras.model.Obra;

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

    private long contar(String trecho) {
        return obras.stream()
                .filter(o -> o.getEtapa() != null && o.getEtapa().getNome() != null
                        && o.getEtapa().getNome().toLowerCase().contains(trecho))
                .count();
    }

    public long getTotal() {
        return obras.size();
    }

    public long getPlanejadas() {
        return contar("planej");
    }

    public long getEmExecucao() {
        return contar("execu");
    }

    public long getCanceladas() {
        return contar("cancel");
    }

    public List<Obra> getObras() {
        return obras;
    }
}