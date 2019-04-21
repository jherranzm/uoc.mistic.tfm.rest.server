package restserverbasicmysql.restserver.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;


/**
 * The persistent class for the kkeys database table.
 * 
 */
@Entity
@XmlRootElement
@Table(name="kkeys")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"creationTime"}, 
        allowGetters = true)
public class SymmetricKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="creation_time", nullable=false)
	private Timestamp creationTime;

	@Column(name = "f", columnDefinition="VARCHAR(100)", nullable=false)
	private String f;

	@Column(name = "k", columnDefinition="LONGTEXT", nullable=false)
	private String k;

	public SymmetricKey() {
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

	public String getF() {
		return this.f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getK() {
		return this.k;
	}

	public void setK(String k) {
		this.k = k;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SymmetricKey [\nid=").append(id).append(", \ncreationTime=").append(creationTime)
				.append(", \nf=").append(f).append(", \nk=").append(k).append("\n]");
		return builder.toString();
	}
	
	

}