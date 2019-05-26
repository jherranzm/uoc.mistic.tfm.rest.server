package restserverbasicmysql.restserver.vo;

import java.io.Serializable;

public class UploadKeyStoreBackUpObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String op;
	private String iv;
	private String enc;
	
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	public String getEnc() {
		return enc;
	}
	public void setEnc(String str) {
		this.enc = str;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UploadKeyStoreBackUpObject [\nop=").append(op).append(", \niv=").append(iv).append(", \nenc=")
				.append(enc).append("\n]");
		return builder.toString();
	}
	
	
}
