package restserverbasicmysql.restserver;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import restserverbasicmysql.restserver.config.FileStorageProperties;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties({ FileStorageProperties.class })
public class RestServerApplication {

	public static final Logger logger = LoggerFactory.getLogger(RestServerApplication.class);

	public static void main(String[] args) {
		logger.info(String.format("*** Init app!", ""));
		SpringApplication.run(RestServerApplication.class, args);
	}

	@Bean
	public JavaMailSender javaMailService() {
		logger.info(String.format("*** Init JavaMailSender!", ""));
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setPort(587);

		javaMailSender.setJavaMailProperties(getMailProperties());
		javaMailSender.setUsername("jherranzm.mistic.tfm@gmail.com");
		javaMailSender.setPassword("Th2S5p2rStr4ngP1ss");

		return javaMailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.debug", "true");

		return properties;
	}
	
}
