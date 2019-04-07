package restserverbasicmysql.restserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@XmlRootElement
@Table(name="invoice")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"dataCreacio"}, 
        allowGetters = true)

public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "uid", columnDefinition="VARCHAR(255)")
    private String uid;

    @Column(name = "taxIdentificationNumber", columnDefinition="VARCHAR(10)")
    private String taxIdentificationNumber;
    
    @Column(name = "corporateName", columnDefinition="VARCHAR(255)")
    private String corporateName;

    @Column(name = "invoiceNumber", columnDefinition="VARCHAR(255)")
    private String invoiceNumber;

    @Column(name = "invoiceTotal", columnDefinition="DOUBLE(15,4)")
    private Double invoiceTotal;

    @Column(name = "totalTaxOutputs", columnDefinition="DOUBLE(15,4)")
    private Double totalTaxOutputs;

    @Column(name = "issueDate", columnDefinition="VARCHAR(255)")
    private String issueDate;
    
    @Column(name = "signedInvoice", columnDefinition="LONGTEXT")
    private String signedInvoice;
    
    @Column(name = "iv", columnDefinition="LONGTEXT")
    private String iv;
    
    @Column(name = "simKey", columnDefinition="LONGTEXT")
    private String simKey;

    public Invoice(String uid
            , String taxIdentificationNumber
            , String corporateName
            , String invoiceNumber
            , Double invoiceTotal
            , Double totalTaxOutputs
            , String issueDate) {
        this.uid = uid;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.corporateName = corporateName;
        this.invoiceNumber = invoiceNumber;
        this.invoiceTotal = invoiceTotal;
        this.totalTaxOutputs = totalTaxOutputs;
        this.issueDate = issueDate;
    }

    public Invoice() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public Double getTotalTaxOutputs() {
        return totalTaxOutputs;
    }

    public void setTotalTaxOutputs(Double totalTaxOutputs) {
        this.totalTaxOutputs = totalTaxOutputs;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getSignedInvoice() {
		return signedInvoice;
	}

	public void setSignedInvoice(String data) {
		this.signedInvoice = data;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getSimKey() {
		return simKey;
	}

	public void setSimKey(String simKey) {
		this.simKey = simKey;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invoice [id=").append(id).append(", uid=").append(uid).append(", taxIdentificationNumber=")
				.append(taxIdentificationNumber).append(", corporateName=").append(corporateName)
				.append(", invoiceNumber=").append(invoiceNumber).append(", invoiceTotal=").append(invoiceTotal)
				.append(", totalTaxOutputs=").append(totalTaxOutputs).append(", issueDate=").append(issueDate)
				.append(", signedInvoice=").append(signedInvoice).append(", iv=").append(iv).append(", simKey=")
				.append(simKey).append("]");
		return builder.toString();
	}

	
	
}
