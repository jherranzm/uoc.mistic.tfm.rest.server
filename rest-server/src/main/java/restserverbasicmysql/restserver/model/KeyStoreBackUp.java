package restserverbasicmysql.restserver.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@XmlRootElement
@Table(schema = "tfm", name = "tbl_keystore")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"creationTime"}, 
        allowGetters = true)

public class KeyStoreBackUp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	  
	@ManyToOne
    @JoinColumn(name = "usuari_id", nullable = false)
	private Usuario usuario;
	
	@Column(name="keystore", columnDefinition="LONGTEXT", nullable=false)
	private String keystore;
	
	@Column(name="iv", columnDefinition="LONGTEXT", nullable=false)
	private String iv;
	
	@Column(name="token", columnDefinition="LONGTEXT", nullable=false)
	private String token;

	@Column(name = "creation_time", nullable = false)
	private Timestamp creationTime;

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

	public String getKeystore() {
		return keystore;
	}

	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public String toString() {
		return String.format(
				"KeyStoreBackUp [\nid=%s, \nusuario=%s, \nkeystore=%s, \niv=%s, \ntoken=%s, \ncreationTime=%s\n]", id,
				usuario, keystore, iv, token, creationTime);
	}
	
	
	
}
