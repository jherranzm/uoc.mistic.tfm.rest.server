package restserverbasicmysql.restserver.model;

import static javax.persistence.FetchType.LAZY;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@XmlRootElement
@Table(name="factura")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"dataCreacio"}, 
        allowGetters = true)
public class Factura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "numFactura", columnDefinition="VARCHAR(255)")
	private String numFactura;
	
	@Lob
	@Basic
	@Column(name = "detallFactura", columnDefinition="LONGBLOB")
    private byte[] detallFactura;
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date dataCreacio;

	public Factura() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public byte[] getDetallFactura() {
		return detallFactura;
	}

	public void setDetallFactura(byte[] detallFactura) {
		this.detallFactura = detallFactura;
	}

	public Date getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(Date dataCreacio) {
		this.dataCreacio = dataCreacio;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Factura [id=");
		builder.append(id);
		builder.append(", numFactura=");
		builder.append(numFactura);
		builder.append(", detallFactura=");
		builder.append(Arrays.toString(detallFactura));
		builder.append(", dataCreacio=");
		builder.append(dataCreacio);
		builder.append("]");
		return builder.toString();
	}

	
	
}
