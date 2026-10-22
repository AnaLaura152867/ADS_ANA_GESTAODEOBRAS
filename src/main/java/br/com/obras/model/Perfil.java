package br.com.obras.model;

/**
 * Perfil de acesso do usuário. Usado pelo filtro administrativo.
 */
public enum Perfil {
    ADMIN("Administrador"),
    USUARIO("Usuário comum");

    private final String descricao;

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
