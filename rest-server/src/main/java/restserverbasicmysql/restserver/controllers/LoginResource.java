package restserverbasicmysql.restserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import restserverbasicmysql.restserver.vo.UserPasswordObject;

@RestController
public class LoginResource {
	
	public static final Logger logger = LoggerFactory.getLogger(LoginResource.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserPasswordObject userPasswordObject) {
		
		logger.info("Received [{}]", userPasswordObject.toString());
        return new ResponseEntity<String>("OK!", HttpStatus.OK);
    }

}
