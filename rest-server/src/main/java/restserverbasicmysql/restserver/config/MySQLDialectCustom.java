package restserverbasicmysql.restserver.config;

public class MySQLDialectCustom extends org.hibernate.dialect.MySQLDialect{

	public MySQLDialectCustom() {
		//registerColumnType( Types.LONGVARBINARY, 16777215L, "longblob" );
	}
}
