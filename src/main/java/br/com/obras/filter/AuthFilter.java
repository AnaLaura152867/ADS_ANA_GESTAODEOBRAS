package br.com.obras.filter;

import br.com.obras.bean.LoginBean;
import java.io.IOException;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de login com restrição administrativa:
 * - qualquer página (exceto login e recursos) exige usuário logado;
 * - a página de Etapas e páginas em /admin/* exigem perfil ADMIN.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    // O CDI entrega o LoginBean da sessão do usuário que fez a requisição.
    @Inject
    private LoginBean login;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contexto = req.getContextPath();
        String caminho = req.getRequestURI().substring(contexto.length());

        // libera a tela de login e os arquivos de CSS, imagens e scripts
        boolean recurso = caminho.startsWith("/jakarta.faces.resource")
                || caminho.startsWith("/javax.faces.resource");
        if (recurso || caminho.equals("/login.xhtml")) {
            chain.doFilter(request, response);
            return;
        }

        // não está logado: volta para o login
        if (!login.isLogado()) {
            res.sendRedirect(contexto + "/login.xhtml");
            return;
        }

        // área administrativa: só perfil ADMIN
        boolean areaAdmin = caminho.startsWith("/admin/") || caminho.equals("/etapas.xhtml");
        if (areaAdmin && !login.isAdmin()) {
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