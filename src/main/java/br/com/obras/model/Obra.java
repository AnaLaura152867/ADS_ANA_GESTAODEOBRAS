package br.com.obras.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Funcionalidade principal do sistema: a obra.
 * (Depois será ligada ao Cliente: um cliente tem várias obras.)
 */
@Entity
@Table(name = "obra")
public class Obra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 255)
    private String endereco;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio", nullable = false)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "previsao_termino", nullable = false)
    private Date previsaoTermino;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal orcamento = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusObra status = StatusObra.PLANEJADA;

    /** Obra com prazo vencido que ainda não foi concluída nem cancelada. */
    public boolean isAtrasada() {
        if (previsaoTermino == null
                || status == StatusObra.CONCLUIDA
                || status == StatusObra.CANCELADA) {
            return false;
        }
        return previsaoTermino.before(new Date());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

    public Date getPrevisaoTermino() { return previsaoTermino; }
    public void setPrevisaoTermino(Date previsaoTermino) { this.previsaoTermino = previsaoTermino; }

    public BigDecimal getOrcamento() { return orcamento; }
    public void setOrcamento(BigDecimal orcamento) { this.orcamento = orcamento; }

    public StatusObra getStatus() { return status; }
    public void setStatus(StatusObra status) { this.status = status; }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Obra)) return false;
        Obra outra = (Obra) obj;
        return id != null && id.equals(outra.id);
    }
}
