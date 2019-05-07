package restserverbasicmysql.restserver.config;

import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
public class Constants {

	public static final String SEC_PROVIDER = "BC";
	public static final String SHA256WITH_RSA = "SHA256withRSA";
	public static final String P12_PASSWORD = "Th2S5p2rStr4ngP1ss";
	
	/**
	 * This value has to be equal to the value of param -name in openssl -export command used to
	 * generate CAkeystore.p12
	 */
	public static final String CA = "ca"; 

	public static final int MILLIS_PER_DAY = 24*60*60*1000;
}
