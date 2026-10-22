package br.com.obras.dao;

import br.com.obras.util.JPAUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * CRUD genérico: salvar (cria ou edita), excluir, buscar por id e listar todos.
 */
public abstract class GenericDAO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Class<T> classe;

    protected GenericDAO(Class<T> classe) {
        this.classe = classe;
    }

    public T salvar(T entidade) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T salvo = em.merge(entidade);
            em.getTransaction().commit();
            return salvo;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void excluir(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T entidade = em.find(classe, id);
            if (entidade != null) {
                em.remove(entidade);
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public T buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(classe, id);
        } finally {
            em.close();
        }
    }

    public List<T> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "select e from " + classe.getSimpleName() + " e order by e.id", classe)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
