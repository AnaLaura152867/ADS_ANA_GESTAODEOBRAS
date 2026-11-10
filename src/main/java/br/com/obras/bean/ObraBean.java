package br.com.obras.bean;

import br.com.obras.model.Obra;
import br.com.obras.model.StatusObra;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class ObraBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Obra obra = new Obra();
    private String filtro;

    // Lista completa dos registros
    private List<Obra> todasAsObras = new ArrayList<>();

    // Lista exibida na tela
    private List<Obra> lista = new ArrayList<>();

    private Long proximoId = 1L;

    @PostConstruct
    public void init() {
        Obra exemplo = new Obra();

        exemplo.setId(proximoId++);
        exemplo.setNome("Construção Residencial");
        exemplo.setEndereco("Rua das Flores, 100");
        exemplo.setDataInicio(new Date());

        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.MONTH, 6);

        exemplo.setPrevisaoTermino(calendario.getTime());
        exemplo.setOrcamento(new BigDecimal("250000.00"));
        exemplo.setStatus(StatusObra.EM_ANDAMENTO);

        todasAsObras.add(exemplo);
        listarTodos();
    }

    public void salvar() {

        if (obra.getNome() == null || obra.getNome().trim().isEmpty()) {
            mensagem(FacesMessage.SEVERITY_ERROR,
                    "Erro", "Informe o nome da obra.");
            return;
        }

        if (obra.getDataInicio() != null
                && obra.getPrevisaoTermino() != null
                && obra.getPrevisaoTermino().before(obra.getDataInicio())) {

            mensagem(FacesMessage.SEVERITY_ERROR,
                    "Erro", "A previsão de término deve ser posterior à data de início.");
            return;
        }

        if (obra.getId() == null) {
            obra.setId(proximoId++);
            todasAsObras.add(obra);

            mensagem(FacesMessage.SEVERITY_INFO,
                    "Sucesso", "Obra cadastrada!");
        } else {
            for (int i = 0; i < todasAsObras.size(); i++) {
                if (todasAsObras.get(i).getId().equals(obra.getId())) {
                    todasAsObras.set(i, obra);
                    break;
                }
            }

            mensagem(FacesMessage.SEVERITY_INFO,
                    "Sucesso", "Obra atualizada!");
        }

        novo();
        listarTodos();
    }

    public void novo() {
        obra = new Obra();
    }

    public void editar(Obra selecionada) {
        obra = selecionada;
    }

    public void excluir(Obra selecionada) {
        todasAsObras.removeIf(item ->
                item.getId().equals(selecionada.getId()));

        listarTodos();

        mensagem(FacesMessage.SEVERITY_INFO,
                "Sucesso", "Obra excluída!");
    }

    public void buscar() {
        if (filtro == null || filtro.trim().isEmpty()) {
            listarTodos();
            return;
        }

        String termo = filtro.trim().toLowerCase();

        lista = new ArrayList<>();

        for (Obra item : todasAsObras) {
            if (item.getNome() != null
                    && item.getNome().toLowerCase().contains(termo)) {
                lista.add(item);
            }
        }
    }

    public void listarTodos() {
        lista = new ArrayList<>(todasAsObras);
        filtro = null;
    }

    private void mensagem(FacesMessage.Severity tipo,
                          String titulo, String texto) {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(tipo, titulo, texto));
    }

    public StatusObra[] getStatusLista() {
        return StatusObra.values();
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
}
