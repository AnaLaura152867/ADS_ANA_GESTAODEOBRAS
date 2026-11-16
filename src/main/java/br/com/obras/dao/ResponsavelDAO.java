package br.com.obras.dao;

import br.com.obras.model.Responsavel;
import br.com.obras.util.JPAUtil;
import java.util.List;
import jakarta.persistence.EntityManager;

public class ResponsavelDAO extends GenericDAO<Responsavel> {

    private static final long serialVersionUID = 1L;

    public ResponsavelDAO() {
        super(Responsavel.class);
    }

    /** Só os responsáveis ativos aparecem na hora de cadastrar uma obra. */
    public List<Responsavel> listarAtivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "select r from Responsavel r where r.ativo = true order by r.nome",
                    Responsavel.class).getResultList();
        } finally {
            em.close();
        }
    }
}