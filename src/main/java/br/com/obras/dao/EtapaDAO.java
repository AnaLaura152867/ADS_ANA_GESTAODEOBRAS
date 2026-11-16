package br.com.obras.dao;

import br.com.obras.model.Etapa;

public class EtapaDAO extends GenericDAO<Etapa> {

    private static final long serialVersionUID = 1L;

    public EtapaDAO() {
        super(Etapa.class);
    }

    /** Cria as etapas padrão na primeira vez em que o banco está vazio. */
    public void garantirPadroes() {
        if (!listarTodos().isEmpty()) {
            return;
        }
        for (String nome : new String[] {"Em planejamento", "Em execução", "Cancelado"}) {
            Etapa etapa = new Etapa();
            etapa.setNome(nome);
            salvar(etapa);
        }
    }
}