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
@Table(name="invoice_data")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"creationTime"}, 
        allowGetters = true)
public class InvoiceData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="creation_time", nullable=false)
	private Timestamp creationTime;

	@Column(nullable=false, length=255, columnDefinition="VARCHAR(255)")
	private String f1;

	@Column(nullable=false, length=255, columnDefinition="VARCHAR(255)")
	private String f2;

	@Column(nullable=false, length=255, columnDefinition="VARCHAR(255)")
	private String f3;

	@Column(nullable=false, length=255, columnDefinition="VARCHAR(255)")
	private String f4;

	private double f5;

	private double f6;

	private double f7;

	public InvoiceData() {
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

	public String getF2() {
		return this.f2;
	}

	public void setF2(String f2) {
		this.f2 = f2;
	}

	public String getF3() {
		return this.f3;
	}

	public void setF3(String f3) {
		this.f3 = f3;
	}

	public String getF4() {
		return this.f4;
	}

	public void setF4(String f4) {
		this.f4 = f4;
	}

	public double getF5() {
		return this.f5;
	}

	public void setF5(double f5) {
		this.f5 = f5;
	}

	public double getF6() {
		return this.f6;
	}

	public void setF6(double f6) {
		this.f6 = f6;
	}

	public double getF7() {
		return this.f7;
	}

	public void setF7(double f7) {
		this.f7 = f7;
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
		builder.append("InvoiceData [id=").append(id).append(", f1=").append(f1).append(", f2=").append(f2)
				.append(", f3=").append(f3).append(", f4=").append(f4).append(", f5=").append(f5).append(", f6=")
				.append(f6).append(", f7=").append(f7).append(", creationTime=").append(creationTime).append("]");
		return builder.toString();
	}
	
	
	

}