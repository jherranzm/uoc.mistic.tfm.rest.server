package restserverbasicmysql.restserver.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import restserverbasicmysql.restserver.model.CustomUser;
import restserverbasicmysql.restserver.model.SymmetricKey;
import restserverbasicmysql.restserver.repos.SymmetricKeyRepository;
import restserverbasicmysql.restserver.vo.UploadKeyObject;

@RestController
public class SymmetricKeysResource {
	
	public static final Logger logger = LoggerFactory.getLogger(SymmetricKeysResource.class);
	
	@Autowired
	private SymmetricKeyRepository symmetricKeyRepository;
	
	@RequestMapping(value="/keys", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveAll(@AuthenticationPrincipal CustomUser user) {
		
		logger.info(String.format("User logged : %s", user.getUsuario().getEmail()));
		
		List<SymmetricKey> symKeys = new ArrayList<SymmetricKey>();
		
		symKeys = symmetricKeyRepository.findAllByUsuario(user.getUsuario());
        if (symKeys.isEmpty()) {
        		logger.warn("Sin claves simétricas!");
    			Map<String, Object> json = new HashMap<String, Object>();
    			json.put("responseCode", HttpStatus.NO_CONTENT.value());
    			json.put("message", "There are no keys in the server.");

            return new ResponseEntity<Map<String, Object>>(json, HttpStatus.OK);
        }
        logger.info("Se han recuperado [{}] claves simétricas del usuario [{}]", symKeys.size(), user.getUsuario());
        return new ResponseEntity<List<SymmetricKey>>(symKeys, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/keys", method = RequestMethod.POST)
    public ResponseEntity<?> post(@AuthenticationPrincipal CustomUser user, @RequestBody UploadKeyObject uploadObject) {
		
		logger.info(String.format("User logged : %s", user.getUsuario().getEmail()));

		logger.info("Objeto recibido:  [{}]", uploadObject);
        SymmetricKey symKey = getKeysFromUploadObject(uploadObject);
        symKey.setUsuario(user.getUsuario());
        
        logger.info("Guardando la clave  [{}]", symKey);
        SymmetricKey newSymKey= new SymmetricKey();
        Optional<SymmetricKey> existingSymKey = symmetricKeyRepository.findByFByUsuario(symKey.getF(), user.getUsuario());

        if(existingSymKey.isPresent()){
        	logger.info("La clave YA está en el sistema!  [{}]", existingSymKey.get());
        	return new ResponseEntity<SymmetricKey>(existingSymKey.get(), HttpStatus.CONFLICT);
        }else {
        	newSymKey = symmetricKeyRepository.save(symKey);
        }
        
        logger.info("Guardada la clave  [{}]", newSymKey);
        return new ResponseEntity<SymmetricKey>(newSymKey, HttpStatus.OK);
    }

	private SymmetricKey getKeysFromUploadObject(UploadKeyObject uploadObject) {
		SymmetricKey newSymKey= new SymmetricKey();
		
		newSymKey.setF(uploadObject.getF());
		newSymKey.setK(uploadObject.getK());
		return newSymKey;
	}

	@RequestMapping(value = "/keys/{f}", method = RequestMethod.GET)
    public ResponseEntity<?> getKey(@AuthenticationPrincipal CustomUser user, @PathVariable("f") String f) {
		
		logger.info(String.format("User logged : %s", user.getUsuario().getEmail()));
		
        logger.info("Recuperando clave con id [{}] del usuario [{}]", f, user.getUsuario());
        Optional<SymmetricKey> existingSymKey = symmetricKeyRepository.findByFByUsuario(f, user.getUsuario());
        if (!existingSymKey.isPresent()) {
            logger.error("Clave con f [{}] del usuario [{}] NO encontrada.", f, user.getUsuario().getEmail());
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("responseCode", HttpStatus.BAD_REQUEST.value());
			json.put("message", String.format("User [%s] Key [%s] NOT FOUND!", user.getUsuario().getEmail(), f));
            return new ResponseEntity<Map<String, Object>>(json, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SymmetricKey>(existingSymKey.get(), HttpStatus.OK);
    }

	@RequestMapping(value = "/keys/{f}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@AuthenticationPrincipal CustomUser user, @PathVariable("f") String f) {
		logger.info("Deleting user {} SymmetricKey {}", user.getUsuario().getEmail(), f);
		Optional<SymmetricKey> existingSymKey = symmetricKeyRepository.findByFByUsuario(f, user.getUsuario());

        if (!existingSymKey.isPresent()) {
        	logger.info("User {} SymmetricKey {} NOT FOUND!", user.getUsuario().getEmail(), f);
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("responseCode", HttpStatus.BAD_REQUEST.value());
			json.put("message", String.format("User [%s] Key [%s] NOT FOUND!", user.getUsuario().getEmail(), f));
            return new ResponseEntity<Map<String, Object>>(json, HttpStatus.NOT_FOUND);
        }

        symmetricKeyRepository.delete(existingSymKey.get());
        logger.info("Deleted user {} SymmetricKey {}", user.getUsuario().getEmail(), f);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("responseCode", HttpStatus.OK.value());
		json.put("message", String.format("User [%s] Key [%s] Correctly deleted!", user.getUsuario().getEmail(), f));
        return new ResponseEntity<Map<String, Object>>(json, HttpStatus.OK);
    }

	@RequestMapping(value = "/keys", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll(@AuthenticationPrincipal CustomUser user) {
		logger.info("Deleting All user {} SymmetricKeys", user.getUsuario().getEmail());
		List<SymmetricKey> existingSymKey = symmetricKeyRepository.findByByUsuario(user.getUsuario());

        if (existingSymKey.isEmpty()) {
        	logger.info("User {} SymmetricKeys NOT FOUND!", user.getUsuario().getEmail());
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("responseCode", HttpStatus.BAD_REQUEST.value());
			json.put("message", String.format("User [%s] Keys NOT FOUND!", user.getUsuario().getEmail()));
            return new ResponseEntity<Map<String, Object>>(json, HttpStatus.NOT_FOUND);
        }

        symmetricKeyRepository.deleteAllByUser(user.getUsuario());
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("responseCode", HttpStatus.OK.value());
		json.put("message", String.format("User [%s] Keys Correctly deleted!", user.getUsuario().getEmail()));
        return new ResponseEntity<Map<String, Object>>(json, HttpStatus.OK);
    }
}
