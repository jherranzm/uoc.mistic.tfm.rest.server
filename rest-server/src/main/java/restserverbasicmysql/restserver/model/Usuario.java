package restserverbasicmysql.restserver.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@XmlRootElement
@Table(schema = "tfm", name = "tbl_usuari")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"creationTime", "numRoles", "roles"}, 
        allowGetters = true)

public class Usuario implements Serializable, Comparable<Usuario> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String pass;
	
	@Column(name="certificate", columnDefinition="LONGTEXT", nullable=true)
	private String certificate;

	private String email;
	private boolean enabled;
	
	@Column(name="comentaris", columnDefinition="LONGTEXT", nullable=false)
	private String comentaris;

	@Column(name="creation_time", nullable=false)
	private Timestamp creationTime;

	/**
	 * (fetch = FetchType.EAGER) Otherwise it does not retrieve ROLES
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "tbl_usuari_role", 
			joinColumns = { @JoinColumn(name = "usuari_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "role_id") }
			)
	private Set<Role> roles;

	@OneToMany(mappedBy="usuario")
	private Set<Token> tokens;

	@Transient
	private long numRoles;

	public Usuario() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return pass;
	}

	public void setPassword(String password) {
		this.pass = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getComentarios() {
		return comentaris;
	}

	public void setComentarios(String comentarios) {
		this.comentaris = comentarios;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
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

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Usuario [\nid=").append(id).append(", \nusername=").append(username).append(", \npass=")
				.append(pass).append(", \ncertificate=").append(certificate).append(", \nemail=").append(email)
				.append(", \nenabled=").append(enabled).append(", \ncomentaris=").append(comentaris)
				.append(", \ncreationTime=").append(creationTime).append(", \nroles=").append(roles)
				.append(", \nnumRoles=").append(numRoles).append("\n]");
		return builder2.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comentaris == null) ? 0 : comentaris.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (comentaris == null) {
			if (other.comentaris != null)
				return false;
		} else if (!comentaris.equals(other.comentaris))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled != other.enabled)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public static class Builder {
		private Long id;
		private String username;
		private String password;
		private String email;
		private boolean enabled;
		private String comentarios;
		private Set<Role> roles;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public Builder comentarios(String comentarios) {
			this.comentarios = comentarios;
			return this;
		}

		public Builder roles(Set<Role> roles) {
			this.roles = roles;
			return this;
		}

		public Usuario build() {
			return new Usuario(this);
		}
	}

	private Usuario(Builder builder) {
		this.id = builder.id;
		this.username = builder.username;
		this.pass = builder.password;
		this.email = builder.email;
		this.enabled = builder.enabled;
		this.comentaris = builder.comentarios;
		this.roles = builder.roles;
	}

	public long getNumRoles() {
		return roles.size();
	}

	public void setNumRoles(long numRoles) {
	}

	@Override
	public int compareTo(Usuario o) {
		return this.username.compareTo(o.getUsername());
	}
}
