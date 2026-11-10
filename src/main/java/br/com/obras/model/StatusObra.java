package br.com.obras.model;

/**
 * SituaÃ§Ã£o geral da obra.
 */
public enum StatusObra {
    PLANEJADA("Planejada"),
    EM_ANDAMENTO("Em andamento"),
    PAUSADA("Pausada"),
    CONCLUIDA("ConcluÃ­da"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusObra(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

