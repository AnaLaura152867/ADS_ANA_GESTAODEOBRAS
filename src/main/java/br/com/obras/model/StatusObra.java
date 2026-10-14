package br.com.obras.model;

/**
 * Situação geral da obra.
 */
public enum StatusObra {
    PLANEJADA("Planejada"),
    EM_ANDAMENTO("Em andamento"),
    PAUSADA("Pausada"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusObra(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
