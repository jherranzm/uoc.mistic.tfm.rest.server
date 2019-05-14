package restserverbasicmysql.restserver.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

/**
 * The persistent class for the backUp database table.
 * 
 */
@Entity
@XmlRootElement
@Table(schema = "tfm", name = "tbl_back_up")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "creationTime" }, allowGetters = true)
public class BackUp implements Serializable {
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

	@Lob
	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String f;

	@Lob
	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String i;

	@Lob
	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String k;

	@Column(nullable = false, length = 255)
	private String f1;

	public BackUp() {
	}

	public Long getId() {
		return this.id;
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
		return this.creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public String getF() {
		return this.f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getI() {
		return this.i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public String getK() {
		return this.k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getF1() {
		return this.f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BackUp [\nid=").append(id).append(", \nusuario=").append(usuario.getEmail()).append(", \ncreationTime=")
				.append(creationTime).append(", \nf=").append(f).append(", \ni=").append(i).append(", \nk=").append(k)
				.append(", \nf1=").append(f1).append("\n]");
		return builder.toString();
	}

	//

}