package restserverbasicmysql.restserver.model;

public class UploadCSR {
	
	private String csr;

	public String getCsr() {
		return csr;
	}

	public void setCsr(String csr) {
		this.csr = csr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UploadCSR [\ncsr=").append(csr).append("\n]");
		return builder.toString();
	}
	
	

}
