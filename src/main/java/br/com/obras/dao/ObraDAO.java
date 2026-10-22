package br.com.obras.dao;

import br.com.obras.model.Obra;
import br.com.obras.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;

public class ObraDAO extends GenericDAO<Obra> {

    private static final long serialVersionUID = 1L;

    public ObraDAO() {
        super(Obra.class);
    }

    /** Listar buscando por um campo específico: nome. */
    public List<Obra> buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "select o from Obra o where lower(o.nome) like :nome order by o.nome",
                    Obra.class)
                    .setParameter("nome", "%" + nome.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
