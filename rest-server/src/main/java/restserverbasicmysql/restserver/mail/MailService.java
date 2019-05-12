package restserverbasicmysql.restserver.mail;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource
	private Environment environment;
	
	@Autowired
	private JavaMailSender javaMailSender;

	
	public void sendMail(String to, String body) {

		logger.info("Sending email...");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom("jherranzm.mistic.tfm@gmail.com");
		message.setSubject("Confirm registration");
		message.setText(body);
		javaMailSender.send(message);

		logger.info("Email Sent!");
	}
	

}
