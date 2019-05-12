package restserverbasicmysql.restserver.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@XmlRootElement
@Table(schema="tfm", name="tbl_role")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"creationTime", "usuarios"}, 
        allowGetters = true)

public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name = "nom", columnDefinition="varchar(255)", nullable=false)
	private String nom;
	
	@Column(name = "comentaris", columnDefinition="LONGTEXT")
	private String comentaris;

	@Column(name="creation_time", nullable=false)
	private Timestamp creationTime;
	
	@ManyToMany(mappedBy = "roles")
	private Set<User> usuarios;
	
	//
	public Role() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getComentaris() {
		return comentaris;
	}

	public void setComentaris(String comentaris) {
		this.comentaris = comentaris;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Set<User> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<User> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [\nid=").append(id).append(", \nnom=").append(nom).append(", \ncomentaris=")
				.append(comentaris).append(", \ncreationTime=").append(creationTime).append("\n]");
		return builder.toString();
	}

	//
	
}
