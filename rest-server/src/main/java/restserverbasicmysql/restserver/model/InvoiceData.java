package restserverbasicmysql.restserver.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

/**
 * The persistent class for the invoice_data database table.
 * 
 */
@Entity
@XmlRootElement
@Table(schema = "tfm", name = "tbl_invoice_data")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "creationTime" }, allowGetters = true)
public class InvoiceData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usuari_id", nullable = false)
	private Usuario usuario;

	@Column(name = "creation_time", nullable = false)
	private Timestamp creationTime;

	@Column(nullable = false, length = 255, columnDefinition = "VARCHAR(255)")
	private String f1;

	@Column(nullable = false, length = 255, columnDefinition = "VARCHAR(255)")
	private String f2;

	@Column(nullable = false, length = 255, columnDefinition = "VARCHAR(255)")
	private String f3;

	@Column(nullable = false, length = 255, columnDefinition = "VARCHAR(255)")
	private String f4;

	private double f5;

	private double f6;

	private double f7;

	@Column(nullable = false, length = 255, columnDefinition = "VARCHAR(255)")
	private String f8;

	public InvoiceData() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public String getF2() {
		return f2;
	}

	public void setF2(String f2) {
		this.f2 = f2;
	}

	public String getF3() {
		return f3;
	}

	public void setF3(String f3) {
		this.f3 = f3;
	}

	public String getF4() {
		return f4;
	}

	public void setF4(String f4) {
		this.f4 = f4;
	}

	public double getF5() {
		return f5;
	}

	public void setF5(double f5) {
		this.f5 = f5;
	}

	public double getF6() {
		return f6;
	}

	public void setF6(double f6) {
		this.f6 = f6;
	}

	public double getF7() {
		return f7;
	}

	public void setF7(double f7) {
		this.f7 = f7;
	}

	public String getF8() {
		return f8;
	}

	public void setF8(String f8) {
		this.f8 = f8;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceData [\nid=").append(id).append(", \nusuario=").append(usuario.getEmail())
				.append(", \ncreationTime=").append(creationTime).append(", \nf1=").append(f1).append(", \nf2=")
				.append(f2).append(", \nf3=").append(f3).append(", \nf4=").append(f4).append(", \nf5=").append(f5)
				.append(", \nf6=").append(f6).append(", \nf7=").append(f7).append(", \nf8=").append(f8).append("\n]");
		return builder.toString();
	}

}