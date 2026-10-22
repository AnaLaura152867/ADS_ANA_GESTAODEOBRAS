package br.com.obras.filter;

import br.com.obras.bean.LoginBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filtro de login com restrição administrativa:
 * - qualquer página (exceto login e recursos) exige usuário logado;
 * - páginas em /admin/* exigem perfil ADMIN.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contexto = req.getContextPath();
        String caminho = req.getRequestURI().substring(contexto.length());

        boolean recurso = caminho.startsWith("/javax.faces.resource");
        if (recurso || caminho.equals("/login.xhtml")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession sessao = req.getSession(false);
        LoginBean login = sessao == null ? null : (LoginBean) sessao.getAttribute("loginBean");

        if (login == null || !login.isLogado()) {
            res.sendRedirect(contexto + "/login.xhtml");
            return;
        }

        if (caminho.startsWith("/admin/") && !login.isAdmin()) {
            res.sendRedirect(contexto + "/index.xhtml");
            return;
        }

        // evita que o botão "voltar" do navegador mostre páginas após o logout
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);

        chain.doFilter(request, response);
    }
}
