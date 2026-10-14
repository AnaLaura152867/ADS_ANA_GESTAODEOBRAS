package br.com.obras.bean;

import br.com.obras.dao.ObraDAO;
import br.com.obras.model.Obra;
import br.com.obras.model.StatusObra;
import br.com.obras.util.Msg;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/** CRUD de obras (funcionalidade principal). */
@ManagedBean
@ViewScoped
public class ObraBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Obra obra = new Obra();
    private String filtro;
    private List<Obra> lista;

    private final ObraDAO dao = new ObraDAO();

    @PostConstruct
    public void init() {
        listarTodos();
    }

    public void salvar() {
        if (obra.getDataInicio() != null && obra.getPrevisaoTermino() != null
                && obra.getPrevisaoTermino().before(obra.getDataInicio())) {
            Msg.erro("A previsão de término não pode ser anterior à data de início.");
            return;
        }
        try {
            dao.salvar(obra);
            Msg.info("Obra salva com sucesso.");
            novo();
            listarTodos();
        } catch (Exception e) {
            Msg.erro("Não foi possível salvar a obra.");
        }
    }

    public void novo() {
        obra = new Obra();
    }

    public void editar(Obra o) {
        obra = o;
    }

    public void excluir(Obra o) {
        try {
            dao.excluir(o.getId());
            Msg.info("Obra excluída.");
            listarTodos();
        } catch (Exception e) {
            Msg.erro("Não foi possível excluir a obra.");
        }
    }

    public void buscar() {
        if (filtro == null || filtro.trim().isEmpty()) {
            listarTodos();
        } else {
            lista = dao.buscarPorNome(filtro.trim());
        }
    }

    public void listarTodos() {
        filtro = null;
        lista = dao.listarTodos();
    }

    public StatusObra[] getStatusLista() {
        return StatusObra.values();
    }

    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }

    public String getFiltro() { return filtro; }
    public void setFiltro(String filtro) { this.filtro = filtro; }

    public List<Obra> getLista() { return lista; }
}
