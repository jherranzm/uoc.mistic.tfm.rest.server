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
@Table(name="back_up")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"creationTime"}, 
        allowGetters = true)
public class BackUp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="creation_time", nullable=false)
	private Timestamp creationTime;

	@Lob
	@Column(nullable=false, columnDefinition="LONGTEXT")
	private String f;

	@Lob
	@Column(nullable=false, columnDefinition="LONGTEXT")
	private String i;

	@Lob
	@Column(nullable=false, columnDefinition="LONGTEXT")
	private String k;


	@Column(nullable=false, length=255)
	private String f1;

	public BackUp() {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		result = prime * result + ((f1 == null) ? 0 : f1.hashCode());
		result = prime * result + ((i == null) ? 0 : i.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((k == null) ? 0 : k.hashCode());
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
		BackUp other = (BackUp) obj;
		if (creationTime == null) {
			if (other.creationTime != null)
				return false;
		} else if (!creationTime.equals(other.creationTime))
			return false;
		if (f == null) {
			if (other.f != null)
				return false;
		} else if (!f.equals(other.f))
			return false;
		if (f1 == null) {
			if (other.f1 != null)
				return false;
		} else if (!f1.equals(other.f1))
			return false;
		if (i == null) {
			if (other.i != null)
				return false;
		} else if (!i.equals(other.i))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (k == null) {
			if (other.k != null)
				return false;
		} else if (!k.equals(other.k))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BackUp [id=").append(id).append(", f1=").append(f1).append(", i=").append(i).append(", k=")
				.append(k).append(", f=").append(f).append(", creationTime=").append(creationTime).append("]");
		return builder.toString();
	}
	
	// 
	
	

}