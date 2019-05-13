package restserverbasicmysql.restserver.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import restserverbasicmysql.restserver.model.CustomUser;
import restserverbasicmysql.restserver.vo.UserPasswordObject;

@RestController
public class LoginResource {
	
	public static final Logger logger = LoggerFactory.getLogger(LoginResource.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(@AuthenticationPrincipal CustomUser user, @RequestBody UserPasswordObject userPasswordObject) {
		
		Map<String, Object> json = new HashMap<String, Object>();
		
		logger.info("Received [{}]", userPasswordObject.toString());
		
		logger.info("User connected [{}]", user.getUsername());
		logger.info("User enabled [{}]", user.isEnabled());
		logger.info("User isCredentialsNonExpired [{}]", user.isCredentialsNonExpired());
		logger.info("User isAccountNonExpired [{}]", user.isAccountNonExpired());
		logger.info("User isAccountNonLocked [{}]", user.isAccountNonLocked());
		
		if(user.isEnabled() 
				&& user.isAccountNonLocked()
				&& user.isAccountNonExpired()
				&& user.isCredentialsNonExpired()
				) {
			
			
			json.put("responseCode", HttpStatus.OK.value());
			json.put("message", "User logged!");
			
			new ResponseEntity<Map<String, Object>>(json, HttpStatus.OK);
		}else {
			json.put("responseCode", HttpStatus.CONFLICT.value());
			json.put("message", "User NOT active in MISTIC Invoice App");
		}
		
        return new ResponseEntity<Map<String, Object>>(json, HttpStatus.OK);
    }

}
