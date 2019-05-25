package restserverbasicmysql.restserver.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import restserverbasicmysql.restserver.model.CustomUser;
import restserverbasicmysql.restserver.model.KeyStoreBackUp;
import restserverbasicmysql.restserver.repos.KeyStoreBackUpRepository;
import restserverbasicmysql.restserver.vo.UploadKeyStoreBackUpObject;

@RestController
public class KeyStoreBackUpResource {
	
	public static final Logger logger = LoggerFactory.getLogger(KeyStoreBackUpResource.class);
	
	@Autowired
	private KeyStoreBackUpRepository keyStoreBackUpRepository;
	
	@RequestMapping(value = "/ksb", method = RequestMethod.GET)
    public ResponseEntity<?> getKeyStoreFromUserLogged(@AuthenticationPrincipal CustomUser user) {
		
		logger.info("Received [{}]", user.getUsuario().getEmail());
		
		Optional<KeyStoreBackUp> optionalKeyStore = keyStoreBackUpRepository.findByUsuario(user.getUsuario());
		if(optionalKeyStore.isPresent()) {
			KeyStoreBackUp theKeyStoreBackUp = optionalKeyStore.get();
			return new ResponseEntity<KeyStoreBackUp>(theKeyStoreBackUp, HttpStatus.OK);
		}else {
			logger.error("The user logged has NO KeyStore backup.");
			return new ResponseEntity<String>("The token is NOT valid.", HttpStatus.CONFLICT);
		}
        
    }

	@RequestMapping(value = "/ksb", method = RequestMethod.POST)
    public ResponseEntity<?> post(
    		@AuthenticationPrincipal CustomUser user, 
    		@RequestBody UploadKeyStoreBackUpObject uploadObject) {
		
		logger.info(String.format("User logged : %s", user.getUsuario().getEmail()));

		logger.info("Objeto recibido:  [{}]", uploadObject);
		if(uploadObject.getOp() == null || uploadObject.getOp().isEmpty()) {
			Map<String, String> response = new HashMap<>();
			response.put("message", "No operation informed");
			response.put("error_code", "ERR_OP_NOT_INFORMED");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
		}else if(!uploadObject.getOp().equals("store")) {
				Map<String, String> response = new HashMap<>();
				response.put("message", "The operation is not supported.");
				response.put("error_code", "ERR_OP_NOT_SUPPORTED");
				return new ResponseEntity<Map<String, String>>(response, HttpStatus.METHOD_NOT_ALLOWED);
		}else if(uploadObject.getStr() == null || uploadObject.getStr().isEmpty()) {
			Map<String, String> response = new HashMap<>();
			response.put("message", "No KeyStore informed");
			response.put("error_code", "ERR_NO_DATA");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
		}
		
        logger.info("Guardando la KeyStore  [{}]", uploadObject);
        
        Optional<KeyStoreBackUp> optionalKeyStoreBackUp = keyStoreBackUpRepository.findByUsuario(user.getUsuario());

        if(optionalKeyStoreBackUp.isPresent()){
        	KeyStoreBackUp existing = optionalKeyStoreBackUp.get();
        	logger.info("La KeyStore del usuario YA está en el sistema. Se actualiza!  [{}]", existing.getUsuario().getEmail());
        	existing.setKeystore(uploadObject.getStr());
        	KeyStoreBackUp updatedKeyStoreBackUp = keyStoreBackUpRepository.save(existing);
        	return new ResponseEntity<KeyStoreBackUp>(updatedKeyStoreBackUp, HttpStatus.OK);
        }else {
        	KeyStoreBackUp newKeyStoreBackUp = new KeyStoreBackUp();
        	newKeyStoreBackUp.setKeystore(uploadObject.getStr());
        	newKeyStoreBackUp.setUsuario(user.getUsuario());
        	newKeyStoreBackUp.setCreationTime(null);
        	newKeyStoreBackUp = keyStoreBackUpRepository.save(newKeyStoreBackUp);
        	logger.info("Guardada la KeyStore  [{}]", newKeyStoreBackUp);
        	return new ResponseEntity<KeyStoreBackUp>(newKeyStoreBackUp, HttpStatus.OK);
        }
    }

}
