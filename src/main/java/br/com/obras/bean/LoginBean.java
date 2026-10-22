package br.com.obras.bean;

import br.com.obras.dao.UsuarioDAO;
import br.com.obras.model.Perfil;
import br.com.obras.model.Usuario;
import br.com.obras.util.Msg;
import br.com.obras.util.SenhaUtil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Controla a sessão do usuário. O nome "loginBean" é o atributo de sessão
 * lido pelo AuthFilter.
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private Usuario usuarioLogado;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public String entrar() {
        Usuario usuario = usuarioDAO.autenticar(login, SenhaUtil.hash(senha == null ? "" : senha));
        if (usuario == null) {
            Msg.erro("Login ou senha inválidos.");
            return null;
        }
        usuarioLogado = usuario;
        senha = null;
        return "/index.xhtml?faces-redirect=true";
    }

    public String sair() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean isLogado() {
        return usuarioLogado != null;
    }

    public boolean isAdmin() {
        return usuarioLogado != null && usuarioLogado.getPerfil() == Perfil.ADMIN;
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Usuario getUsuarioLogado() { return usuarioLogado; }
}
