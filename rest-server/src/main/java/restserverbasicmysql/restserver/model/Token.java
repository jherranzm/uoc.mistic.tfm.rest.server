package restserverbasicmysql.restserver.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@XmlRootElement
@Table(schema = "tfm", name = "tbl_token")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"creationTime"}, 
        allowGetters = true)

public class Token implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	  
	@ManyToOne
    @JoinColumn(name = "usuari_id", nullable = false)
	private Usuario usuario;
	
	@Column(name="token", columnDefinition="LONGTEXT", nullable=false)
	private String token;
	  
	private String resum;
	  
	@Temporal(TemporalType.TIMESTAMP)
	private Date valid_from;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date valid_to;
	  
	@Column(name="creation_time", nullable=false)
	private Timestamp creationTime;
	
	private boolean used;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getResum() {
		return resum;
	}

	public void setResum(String resum) {
		this.resum = resum;
	}

	public Date getValid_from() {
		return valid_from;
	}

	public void setValid_from(Date valid_from) {
		this.valid_from = valid_from;
	}

	public Date getValid_to() {
		return valid_to;
	}

	public void setValid_to(Date valid_to) {
		this.valid_to = valid_to;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Token [\nid=").append(id).append(", \nusuario=").append(usuario).append(", \ntoken=")
				.append(token).append(", \nresum=").append(resum).append(", \nvalid_from=").append(valid_from)
				.append(", \nvalid_to=").append(valid_to).append(", \ncreationTime=").append(creationTime)
				.append(", \nused=").append(used).append("\n]");
		return builder.toString();
	}
	
	
	
}
