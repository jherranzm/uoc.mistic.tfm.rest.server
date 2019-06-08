package restserverbasicmysql.restserver.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadObject {
	
    private String uidfactura;
    
    @JsonProperty("invoice_number")
    private String invoiceNumber;
    
    @JsonProperty("tax_identification_number")
    private String taxIdentificationNumber;
    
    @JsonProperty("corporate_name")
    private String corporateName;
    
    private String total;
    
    @JsonProperty("total_gross_amount")
    private String totalGrossAmount;
    
    @JsonProperty("total_tax_outputs")
    private String totalTaxOutputs;
    
    @JsonProperty("issue_data")
    private String issueData;
    
    private String file;
    
    private String iv;
    private String key;
    
    
    
    
	public String getUidfactura() {
		return uidfactura;
	}
	public void setUidfactura(String uidfactura) {
		this.uidfactura = uidfactura;
	}
	public String getInvoicenumber() {
		return invoiceNumber;
	}
	public void setInvoicenumber(String invoicenumber) {
		this.invoiceNumber = invoicenumber;
	}
	public String getSeller() {
		return taxIdentificationNumber;
	}
	public void setSeller(String seller) {
		this.taxIdentificationNumber = seller;
	}
	public String getCorporateName() {
		return corporateName;
	}
	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTotalgrossamount() {
		return totalGrossAmount;
	}
	public void setTotalgrossamount(String totalgrossamount) {
		this.totalGrossAmount = totalgrossamount;
	}
	public String getTotaltaxoutputs() {
		return totalTaxOutputs;
	}
	public void setTotaltaxoutputs(String totaltaxoutputs) {
		this.totalTaxOutputs = totaltaxoutputs;
	}
	public String getIssueData() {
		return issueData;
	}
	public void setIssueData(String data) {
		this.issueData = data;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UploadObject [\nuidfactura=").append(uidfactura).append(", \ninvoiceNumber=")
				.append(invoiceNumber).append(", \ntaxIdentificationNumber=").append(taxIdentificationNumber)
				.append(", \ncorporateName=").append(corporateName).append(", \ntotal=").append(total)
				.append(", \ntotalGrossAmount=").append(totalGrossAmount).append(", \ntotalTaxOutputs=")
				.append(totalTaxOutputs).append(", \nissueData=").append(issueData).append(", \nfile=").append(file)
				.append(", \niv=").append(iv).append(", \nkey=").append(key).append("\n]");
		return builder.toString();
	}
    
 
 	
}
