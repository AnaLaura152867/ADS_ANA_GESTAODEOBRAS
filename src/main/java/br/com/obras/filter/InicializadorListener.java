package br.com.obras.filter;

import br.com.obras.dao.UsuarioDAO;
import br.com.obras.model.Perfil;
import br.com.obras.model.Usuario;
import br.com.obras.util.JPAUtil;
import br.com.obras.util.SenhaUtil;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Na primeira execução (banco vazio) cria o administrador padrão:
 * login "admin" / senha "admin123". Troque a senha após o primeiro acesso.
 */
@WebListener
public class InicializadorListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UsuarioDAO dao = new UsuarioDAO();
        if (dao.contar() == 0) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setLogin("admin");
            admin.setSenha(SenhaUtil.hash("admin123"));
            admin.setPerfil(Perfil.ADMIN);
            dao.salvar(admin);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.fechar();
    }
}
