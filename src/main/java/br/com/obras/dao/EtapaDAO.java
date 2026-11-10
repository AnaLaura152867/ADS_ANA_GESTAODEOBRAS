package br.com.obras.dao;

import br.com.obras.model.Etapa;
import br.com.obras.model.Obra;
import br.com.obras.util.JPAUtil;

import java.util.List;

import jakarta.persistence.EntityManager;

public class EtapaDAO extends GenericDAO<Etapa> {

    private static final long serialVersionUID = 1L;

    public EtapaDAO() {
        super(Etapa.class);
    }

    @Override
    public List<Etapa> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT e FROM Etapa e JOIN FETCH e.obra ORDER BY e.id",
                    Etapa.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Etapa> listarPorObra(Obra obra) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT e FROM Etapa e JOIN FETCH e.obra WHERE e.obra.id = :obraId ORDER BY e.id",
                    Etapa.class)
                    .setParameter("obraId", obra.getId())
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
