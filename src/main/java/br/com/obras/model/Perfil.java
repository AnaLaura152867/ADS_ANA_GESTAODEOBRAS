package br.com.obras.model;

/**
 * Perfil de acesso do usuÃ¡rio. Usado pelo filtro administrativo.
 */
public enum Perfil {
    ADMIN("Administrador"),
    USUARIO("UsuÃ¡rio comum");

    private final String descricao;

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

