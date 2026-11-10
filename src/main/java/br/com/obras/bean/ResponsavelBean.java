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

    private Responsavel responsavel = new Responsavel();
    private ResponsavelDAO dao = new ResponsavelDAO();

    private String filtro;
    private List<Responsavel> lista = new ArrayList<>();

    @PostConstruct
    public void init() {
        listarTodos();
    }

    public void salvar() {
        if (responsavel.getNome() == null
                || responsavel.getNome().trim().isEmpty()) {
            mensagem(FacesMessage.SEVERITY_ERROR,
                    "Erro", "Informe o nome do responsável.");
            return;
        }

        if (responsavel.getCargo() == null
                || responsavel.getCargo().trim().isEmpty()) {
            mensagem(FacesMessage.SEVERITY_ERROR,
                    "Erro", "Informe o cargo.");
            return;
        }

        if (responsavel.getEmail() == null
                || responsavel.getEmail().trim().isEmpty()) {
            mensagem(FacesMessage.SEVERITY_ERROR,
                    "Erro", "Informe o e-mail.");
            return;
        }

        try {
            dao.salvar(responsavel);

            mensagem(FacesMessage.SEVERITY_INFO,
                    "Sucesso", "Responsável salvo com sucesso!");

            novo();
            listarTodos();

        } catch (RuntimeException e) {
            mensagem(FacesMessage.SEVERITY_ERROR,
                    "Erro", "Não foi possível salvar o responsável.");
        }
    }

    public void novo() {
        responsavel = new Responsavel();
    }

    public void editar(Responsavel selecionado) {
        responsavel = dao.buscarPorId(selecionado.getId());
    }

    public void excluir(Responsavel selecionado) {
        try {
            dao.excluir(selecionado.getId());
            listarTodos();

            mensagem(FacesMessage.SEVERITY_INFO,
                    "Sucesso", "Responsável excluído com sucesso!");

        } catch (RuntimeException e) {
            mensagem(FacesMessage.SEVERITY_ERROR,
                    "Erro", "Não foi possível excluir o responsável.");
        }
    }

    public void buscar() {
        if (filtro == null || filtro.trim().isEmpty()) {
            listarTodos();
            return;
        }

        String termo = filtro.trim().toLowerCase();
        lista = new ArrayList<>();

        for (Responsavel item : dao.listarTodos()) {
            if ((item.getNome() != null
                    && item.getNome().toLowerCase().contains(termo))
                    || (item.getCpf() != null
                    && item.getCpf().contains(termo))
                    || (item.getEmail() != null
                    && item.getEmail().toLowerCase().contains(termo))) {
                lista.add(item);
            }
        }
    }

    public void listarTodos() {
        lista = dao.listarTodos();
        filtro = null;
    }

    private void mensagem(FacesMessage.Severity tipo,
                          String titulo, String texto) {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(tipo, titulo, texto));
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<Responsavel> getLista() {
        return lista;
    }
}
