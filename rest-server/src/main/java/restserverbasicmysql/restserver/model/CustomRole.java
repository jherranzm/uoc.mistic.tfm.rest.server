package restserverbasicmysql.restserver.model;

import org.springframework.security.core.GrantedAuthority;

public class CustomRole implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	private String name;

	@Override
	public String getAuthority() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomRole [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
	

}
