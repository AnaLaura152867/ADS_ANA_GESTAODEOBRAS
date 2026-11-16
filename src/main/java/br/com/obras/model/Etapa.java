package br.com.obras.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "etapa")
public class Etapa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String nome;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getClasse() {
        String n = nome == null ? "" : nome.toLowerCase();
        if (n.contains("planej")) return "st-EM_PLANEJAMENTO";
        if (n.contains("execu")) return "st-EM_ANDAMENTO";
        if (n.contains("conclu")) return "st-CONCLUIDA";
        return "st-CANCELADA";
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Etapa)) {
            return false;
        }
        Etapa outra = (Etapa) obj;
        return id != null && id.equals(outra.id);
    }
}