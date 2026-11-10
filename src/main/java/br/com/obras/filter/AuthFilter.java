package br.com.obras.filter;

import br.com.obras.bean.LoginBean;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro de login com restriÃ§Ã£o administrativa:
 * - qualquer pÃ¡gina (exceto login e recursos) exige usuÃ¡rio logado;
 * - pÃ¡ginas em /admin/* exigem perfil ADMIN.
 */
// @WebFilter("/*")
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

        // evita que o botÃ£o "voltar" do navegador mostre pÃ¡ginas apÃ³s o logout
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);

        chain.doFilter(request, response);
    }
}

