package restserverbasicmysql.restserver.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@XmlRootElement
@Table(schema = "tfm", name="tbl_invoice")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"usuari_id"}, allowGetters = true)
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usuari_id", nullable = false)
	@JsonIgnore
	private Usuario usuario;

    @Column(name = "uid", columnDefinition="VARCHAR(255)")
    private String uid;

    @Column(name = "taxIdentificationNumber", columnDefinition="VARCHAR(10)")
    private String taxIdentificationNumber;
    
    @Column(name = "corporateName", columnDefinition="VARCHAR(255)")
    private String corporateName;

    @Column(name = "invoiceNumber", columnDefinition="VARCHAR(255)")
    private String invoiceNumber;

    @Column(name = "invoiceTotal", columnDefinition="ARCHAR(255)")
    private String invoiceTotal;

    @Column(name = "totalTaxOutputs", columnDefinition="ARCHAR(255)")
    private String totalTaxOutputs;

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
            , String invoiceTotal
            , String totalTaxOutputs
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public String getInvoiceTotal() {
		return invoiceTotal;
	}

	public void setInvoiceTotal(String invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}

	public String getTotalTaxOutputs() {
		return totalTaxOutputs;
	}

	public void setTotalTaxOutputs(String totalTaxOutputs) {
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

	public void setSignedInvoice(String signedInvoice) {
		this.signedInvoice = signedInvoice;
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
		builder.append("Invoice [\nid=").append(id).append(", \nusuario=").append(usuario).append(", \nuid=")
				.append(uid).append(", \ntaxIdentificationNumber=").append(taxIdentificationNumber)
				.append(", \ncorporateName=").append(corporateName).append(", \ninvoiceNumber=").append(invoiceNumber)
				.append(", \ninvoiceTotal=").append(invoiceTotal).append(", \ntotalTaxOutputs=").append(totalTaxOutputs)
				.append(", \nissueDate=").append(issueDate).append(", \nsignedInvoice=").append(signedInvoice)
				.append(", \niv=").append(iv).append(", \nsimKey=").append(simKey).append("\n]");
		return builder.toString();
	}


    
    
}
