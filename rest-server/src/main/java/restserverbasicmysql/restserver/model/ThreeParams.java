package restserverbasicmysql.restserver.model;

public class ThreeParams {
    private String uidfactura;
    private String invoicenumber;
    private String seller; // taxIdentificationNumber
    private String total;
    private String totaltaxoutputs;
    
    private String data;
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
		return invoicenumber;
	}
	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTotaltaxoutputs() {
		return totaltaxoutputs;
	}
	public void setTotaltaxoutputs(String totaltaxoutputs) {
		this.totaltaxoutputs = totaltaxoutputs;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
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
		builder.append("ThreeParams [uidfactura=").append(uidfactura)
		.append(", invoicenumber=").append(invoicenumber)
				.append(", seller=").append(seller)
				.append(", total=").append(total)
				.append(", totaltaxoutputs=").append(totaltaxoutputs)
				.append(", data=").append(data)
				.append(", file=").append(file)
				.append(", iv=").append(iv)
				.append(", key=").append(key).append("]");
		return builder.toString();
	}
    
 
 	
}
