package br.com.obras.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "responsavel")
public class Responsavel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 100)
    private String cargo;

    @Column(nullable = false)
    private boolean ativo = true;

    // Um responsável pode cuidar de várias obras.
    @OneToMany(mappedBy = "responsavel", fetch = FetchType.LAZY)
    private List<Obra> obras = new ArrayList<>();

    public String getSituacao() {
        return ativo ? "Ativo" : "Inativo";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public List<Obra> getObras() { return obras; }
    public void setObras(List<Obra> obras) { this.obras = obras; }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Responsavel)) {
            return false;
        }
        Responsavel outro = (Responsavel) obj;
        return id != null && id.equals(outro.id);
    }
}