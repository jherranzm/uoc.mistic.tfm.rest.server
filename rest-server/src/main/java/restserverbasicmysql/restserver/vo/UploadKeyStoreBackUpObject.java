package restserverbasicmysql.restserver.vo;

import java.io.Serializable;

public class UploadKeyStoreBackUpObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String op;
	private String str;
	
	
	
	
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UploadKeyStoreBackUpObject [\nop=").append(op).append(", \nstr=").append(str).append("\n]");
		return builder.toString();
	}
	
	
}
