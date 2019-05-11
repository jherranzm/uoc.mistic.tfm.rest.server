package restserverbasicmysql.restserver.vo;

public class UserPasswordObject {
	
	private String op;
	private String username;
	private String pass;
	
	
	
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserPasswordObject [\nop=").append(op).append(", \nusername=").append(username)
				.append(", \npass=").append(pass).append("\n]");
		return builder.toString();
	}
	
	
	
	
	

}
