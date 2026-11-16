package br.com.obras.bean;

import br.com.obras.dao.EtapaDAO;
import br.com.obras.model.Etapa;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class EtapaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final EtapaDAO dao = new EtapaDAO();

    private Etapa etapa = new Etapa();
    private List<Etapa> lista = new ArrayList<>();

    @PostConstruct
    public void init() {
        dao.garantirPadroes();
        listar();
    }

    public void salvar() {
        if (etapa.getNome() == null || etapa.getNome().trim().isEmpty()) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Informe o nome da etapa.");
            return;
        }

        boolean nova = etapa.getId() == null;

        try {
            dao.salvar(etapa);
            mensagem(FacesMessage.SEVERITY_INFO, "Sucesso",
                    nova ? "Etapa cadastrada!" : "Etapa atualizada!");
            novo();
            listar();
        } catch (RuntimeException e) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro",
                    "Não foi possível salvar a etapa no banco de dados.");
        }
    }

    public void novo() {
        etapa = new Etapa();
    }

    public void editar(Etapa selecionada) {
        etapa = selecionada;
    }

    public void excluir(Etapa selecionada) {
        try {
            dao.excluir(selecionada.getId());
            listar();
            mensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Etapa excluída!");
        } catch (RuntimeException e) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro",
                    "Não foi possível excluir: a etapa está ligada a uma ou mais obras.");
        }
    }

    public void listar() {
        lista = dao.listarTodos();
    }

    private void mensagem(FacesMessage.Severity tipo, String titulo, String texto) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, titulo, texto));
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public List<Etapa> getLista() {
        return lista;
    }
}