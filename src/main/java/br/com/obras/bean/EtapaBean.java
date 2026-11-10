package br.com.obras.bean;

import br.com.obras.dao.EtapaDAO;
import br.com.obras.dao.ObraDAO;
import br.com.obras.model.Etapa;
import br.com.obras.model.Obra;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class EtapaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Etapa etapa = new Etapa();

    private List<Etapa> lista = new ArrayList<>();
    private List<Obra> obras = new ArrayList<>();

    private String filtro;

    private final EtapaDAO etapaDAO = new EtapaDAO();

    @PostConstruct
    public void init() {
        carregarObras();
        listarTodos();
    }

    public void carregarObras() {
        try {
            ObraDAO obraDAO = new ObraDAO();
            obras = obraDAO.listarTodos();
        } catch (RuntimeException e) {
            obras = new ArrayList<>();

            mensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "Não foi possível carregar as obras."
            );
        }
    }

    public void salvar() {

        if (etapa.getNome() == null
                || etapa.getNome().trim().isEmpty()) {

            mensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "Informe o nome da etapa."
            );
            return;
        }

        if (etapa.getObra() == null
                || etapa.getObra().getId() == null) {

            mensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "Selecione uma obra."
            );
            return;
        }

        if (etapa.getDataInicio() != null
                && etapa.getDataFim() != null
                && etapa.getDataFim().isBefore(etapa.getDataInicio())) {

            mensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "A data final deve ser posterior à data inicial."
            );
            return;
        }

        if (etapa.getStatus() == null
                || etapa.getStatus().trim().isEmpty()) {

            etapa.setStatus("PLANEJADA");
        }

        try {

            etapaDAO.salvar(etapa);

            mensagem(
                    FacesMessage.SEVERITY_INFO,
                    "Sucesso",
                    "Etapa salva com sucesso!"
            );

            novo();
            listarTodos();

        } catch (RuntimeException e) {

            mensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "Não foi possível salvar a etapa."
            );
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

            etapaDAO.excluir(selecionada.getId());

            listarTodos();

            mensagem(
                    FacesMessage.SEVERITY_INFO,
                    "Sucesso",
                    "Etapa excluída com sucesso!"
            );

        } catch (RuntimeException e) {

            mensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "Não foi possível excluir a etapa."
            );
        }
    }

    public void buscar() {

        if (filtro == null || filtro.trim().isEmpty()) {
            listarTodos();
            return;
        }

        String termo = filtro.trim().toLowerCase();

        lista = new ArrayList<>();

        for (Etapa item : etapaDAO.listarTodos()) {

            if (item.getNome() != null
                    && item.getNome().toLowerCase().contains(termo)) {

                lista.add(item);
            }
        }
    }

    public void listarTodos() {

        try {

            lista = etapaDAO.listarTodos();
            filtro = null;

        } catch (RuntimeException e) {

            lista = new ArrayList<>();

            mensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "Não foi possível listar as etapas."
            );
        }
    }

    private void mensagem(
            FacesMessage.Severity tipo,
            String titulo,
            String texto) {

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(tipo, titulo, texto)
        );
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

    public List<Obra> getObras() {
        return obras;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public LocalDate getHoje() {
        return LocalDate.now();
    }
}
