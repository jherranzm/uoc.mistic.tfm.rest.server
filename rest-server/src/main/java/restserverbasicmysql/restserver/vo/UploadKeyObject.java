package restserverbasicmysql.restserver.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadKeyObject {
    
    @JsonProperty("f")
    private String f;
    
    @JsonProperty("k")
    private String k;

	public UploadKeyObject(String f, String k) {
		super();
		this.f = f;
		this.k = k;
	}

	public UploadKeyObject() {
		super();
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UploadKeyObject [\nf=").append(f).append(", \nk=").append(k).append("\n]");
		return builder.toString();
	}



}
