package restserverbasicmysql.restserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerStatusResource {
	
	public static final Logger logger = LoggerFactory.getLogger(ServerStatusResource.class);
	
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ResponseEntity<?> getStatus() {
		
		logger.info("Requesting status... [{}]", "");
		return new ResponseEntity<String>("ACTIVE", HttpStatus.OK);
	}

}
