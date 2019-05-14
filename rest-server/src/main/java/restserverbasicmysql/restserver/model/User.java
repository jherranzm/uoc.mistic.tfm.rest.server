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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@XmlRootElement
@Table(schema = "tfm", name="tbl_users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"creationTime"}, 
        allowGetters = true)
public class User implements Serializable, Comparable<User> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name = "pass", columnDefinition="varchar(255)", nullable=false)
	private String pass;

	@Column(nullable=false, length=25)
	private String user;

	@Column(name="creation_time", nullable=false)
	private Timestamp creationTime;
	
	@ManyToMany
	@JoinTable(
			name = "tbl_usuari_role",
				joinColumns = { @JoinColumn(name = "usuari_id") },
				inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles;
	
	@Transient
	private long numRoles;


	public User() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	@Override
	public int compareTo(User o) {
		return this.user.compareTo(o.getUser());
	}

}