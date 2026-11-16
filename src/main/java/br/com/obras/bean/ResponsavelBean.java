package br.com.obras.bean;

import br.com.obras.dao.ResponsavelDAO;
import br.com.obras.model.Responsavel;

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
public class ResponsavelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ResponsavelDAO dao = new ResponsavelDAO();

    private Responsavel responsavel = new Responsavel();
    private List<Responsavel> lista = new ArrayList<>();

    @PostConstruct
    public void init() {
        listar();
    }

    public void salvar() {
        if (responsavel.getNome() == null || responsavel.getNome().trim().isEmpty()) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Informe o nome do responsável.");
            return;
        }

        if (responsavel.getCargo() == null || responsavel.getCargo().trim().isEmpty()) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Informe o cargo do responsável.");
            return;
        }

        boolean novo = responsavel.getId() == null;

        try {
            dao.salvar(responsavel);
            mensagem(FacesMessage.SEVERITY_INFO, "Sucesso",
                    novo ? "Responsável cadastrado!" : "Responsável atualizado!");
            novo();
            listar();
        } catch (RuntimeException e) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro",
                    "Não foi possível salvar o responsável no banco de dados.");
        }
    }

    public void novo() {
        responsavel = new Responsavel();
    }

    public void editar(Responsavel selecionado) {
        responsavel = selecionado;
    }

    public void excluir(Responsavel selecionado) {
        try {
            dao.excluir(selecionado.getId());
            listar();
            mensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Responsável excluído!");
        } catch (RuntimeException e) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro",
                    "Não foi possível excluir: o responsável está ligado a uma ou mais obras. "
                    + "Marque-o como inativo.");
        }
    }

    public void listar() {
        lista = dao.listarTodos();
    }

    private void mensagem(FacesMessage.Severity tipo, String titulo, String texto) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, titulo, texto));

            }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public List<Responsavel> getLista() {
        return lista;
    }
}