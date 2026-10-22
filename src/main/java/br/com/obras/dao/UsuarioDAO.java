package br.com.obras.dao;

import br.com.obras.model.Usuario;
import br.com.obras.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;

public class UsuarioDAO extends GenericDAO<Usuario> {

    private static final long serialVersionUID = 1L;

    public UsuarioDAO() {
        super(Usuario.class);
    }

    /** Listar buscando por um campo específico: nome. */
    public List<Usuario> buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "select u from Usuario u where lower(u.nome) like :nome order by u.nome",
                    Usuario.class)
                    .setParameter("nome", "%" + nome.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /** Retorna o usuário que tem esse login e esse hash de senha, ou null. */
    public Usuario autenticar(String login, String senhaHash) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Usuario> lista = em.createQuery(
                    "select u from Usuario u where u.login = :login and u.senha = :senha",
                    Usuario.class)
                    .setParameter("login", login)
                    .setParameter("senha", senhaHash)
                    .getResultList();
            return lista.isEmpty() ? null : lista.get(0);
        } finally {
            em.close();
        }
    }

    public long contar() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("select count(u) from Usuario u", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
