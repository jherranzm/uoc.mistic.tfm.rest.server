package restserverbasicmysql.restserver.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import restserverbasicmysql.restserver.model.KeyStoreBackUp;
import restserverbasicmysql.restserver.repos.KeyStoreBackUpRepository;

@RestController
public class KeyStoreBackUpRetrieveResource {
	
	public static final Logger logger = LoggerFactory.getLogger(KeyStoreBackUpRetrieveResource.class);
	
	@Autowired
	private KeyStoreBackUpRepository keyStoreBackUpRepository;
	
	@RequestMapping(value = "/ret/token/{theToken}", method = RequestMethod.GET)
	public ResponseEntity<?> getKeyStoreFromUserLoggedAndToken(@PathVariable("theToken") String theToken) {
		
		logger.info("Received [{}]", theToken);
		
		Optional<KeyStoreBackUp> optionalKeyStore = keyStoreBackUpRepository.findByToken(theToken);
		if(optionalKeyStore.isPresent()) {
			KeyStoreBackUp theKeyStoreBackUp = optionalKeyStore.get();
			return new ResponseEntity<KeyStoreBackUp>(theKeyStoreBackUp, HttpStatus.OK);
		}else {
			logger.error("The user logged has NO KeyStore backup.");
			return new ResponseEntity<String>("The token is NOT valid.", HttpStatus.CONFLICT);
		}
		
	}


}
