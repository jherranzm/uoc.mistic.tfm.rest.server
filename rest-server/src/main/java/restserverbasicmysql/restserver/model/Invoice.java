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

    @Column(name = "issueDate", columnDefinition="DATE")
    private java.sql.Date issueDate;

    public Invoice(String uid
            , String taxIdentificationNumber
            , String corporateName
            , String invoiceNumber
            , Double invoiceTotal
            , Double totalTaxOutputs
            , java.sql.Date issueDate) {
        this.uid = uid;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.corporateName = corporateName;
        this.invoiceNumber = invoiceNumber;
        this.invoiceTotal = invoiceTotal;
        this.totalTaxOutputs = totalTaxOutputs;
        this.issueDate = issueDate;
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

    public java.sql.Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(java.sql.Date issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Invoice{");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", taxIdentificationNumber='").append(taxIdentificationNumber).append('\'');
        sb.append(", corporateName='").append(corporateName).append('\'');
        sb.append(", invoiceNumber='").append(invoiceNumber).append('\'');
        sb.append(", invoiceTotal=").append(invoiceTotal);
        sb.append(", totalTaxOutputs=").append(totalTaxOutputs);
        sb.append(", issueDate=").append(issueDate);
        sb.append('}');
        return sb.toString();
    }
}
