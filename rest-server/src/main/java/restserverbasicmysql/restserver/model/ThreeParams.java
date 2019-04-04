package restserverbasicmysql.restserver.model;

public class ThreeParams {
    private String uidfactura;
    private String seller;
    private String total;
    private String data;
    
    
    
	public String getUidfactura() {
		return uidfactura;
	}
	public void setUidfactura(String uidfactura) {
		this.uidfactura = uidfactura;
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ThreeParams [uidfactura=").append(uidfactura).append(", seller=").append(seller)
				.append(", total=").append(total).append(", data=").append(data).append("]");
		return builder.toString();
	}
    
    
    //
    
}
