package restserverbasicmysql.restserver.exceptions;

public class FacturaNotFoundException extends Exception {

	public FacturaNotFoundException(String string) {
		System.out.println("FNFE:" + string);
	}

	private static final long serialVersionUID = 1L;

}
