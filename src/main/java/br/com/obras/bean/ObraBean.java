package br.com.obras.bean;

import br.com.obras.dao.EtapaDAO;
import br.com.obras.dao.ObraDAO;
import br.com.obras.dao.ResponsavelDAO;
import br.com.obras.model.Etapa;
import br.com.obras.model.Obra;
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
public class ObraBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ObraDAO dao = new ObraDAO();
    private final ResponsavelDAO responsavelDAO = new ResponsavelDAO();
    private final EtapaDAO etapaDAO = new EtapaDAO();

    private Obra obra = new Obra();
    private String filtro;

    private List<Obra> lista = new ArrayList<>();
    private List<Responsavel> responsaveis = new ArrayList<>();
    private List<Etapa> etapas = new ArrayList<>();

    @PostConstruct
    public void init() {
        etapaDAO.garantirPadroes();
        etapas = etapaDAO.listarTodos();
        carregarResponsaveis();
        listarTodos();
    }

    /** Responsáveis ativos; na edição mantém o atual, mesmo que esteja inativo. */
    private void carregarResponsaveis() {
        responsaveis = new ArrayList<>(responsavelDAO.listarAtivos());
        Responsavel atual = obra.getResponsavel();
        if (atual != null && !responsaveis.contains(atual)) {
            responsaveis.add(atual);
        }
    }

    public void salvar() {
        if (obra.getNome() == null || obra.getNome().trim().isEmpty()) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Informe o nome da obra.");
            return;
        }

        if (obra.getResponsavel() == null) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro",
                    "Selecione um responsável já cadastrado. Cadastre-o primeiro em Responsáveis.");
            return;
        }

        if (obra.getEtapa() == null) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Selecione o status da obra.");
            return;
        }

        if (obra.getDataInicio() != null
                && obra.getPrevisaoTermino() != null
                && obra.getPrevisaoTermino().before(obra.getDataInicio())) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro",
                    "A previsão de entrega deve ser posterior à data de início.");
            return;
        }

        boolean nova = obra.getId() == null;

        try {
            dao.salvar(obra);
            mensagem(FacesMessage.SEVERITY_INFO, "Sucesso",
                    nova ? "Obra cadastrada!" : "Obra atualizada!");
            novo();
            listarTodos();
        } catch (RuntimeException e) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro",
                    "Não foi possível salvar a obra no banco de dados.");
        }
    }

    public void novo() {
        obra = new Obra();
        carregarResponsaveis();
    }

    public void editar(Obra selecionada) {
        obra = selecionada;
        carregarResponsaveis();

            }

    public void excluir(Obra selecionada) {
        try {
            dao.excluir(selecionada.getId());
            listarTodos();
            mensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Obra excluída!");
        } catch (RuntimeException e) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir a obra.");
        }
    }

    public void buscar() {
        if (filtro == null || filtro.trim().isEmpty()) {
            listarTodos();
            return;
        }
        lista = dao.buscarPorNome(filtro.trim());
    }

    public void listarTodos() {
        lista = dao.listarTodos();
        filtro = null;
    }

    private void mensagem(FacesMessage.Severity tipo, String titulo, String texto) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, titulo, texto));
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<Obra> getLista() {
        return lista;
    }

    public List<Responsavel> getResponsaveis() {
        return responsaveis;
    }

    public List<Etapa> getEtapas() {
        return etapas;
    }
}