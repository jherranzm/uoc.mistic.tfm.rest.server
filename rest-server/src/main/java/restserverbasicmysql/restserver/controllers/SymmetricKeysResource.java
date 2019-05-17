package restserverbasicmysql.restserver.controllers;

import java.util.ArrayList;
import java.util.List;
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

import restserverbasicmysql.restserver.error.CustomErrorType;
import restserverbasicmysql.restserver.model.CustomUser;
import restserverbasicmysql.restserver.model.SymmetricKey;
import restserverbasicmysql.restserver.repos.SymmetricKeyRepository;
import restserverbasicmysql.restserver.vo.UploadKeyObject;

@RestController
public class SymmetricKeysResource {
	
	public static final Logger logger = LoggerFactory.getLogger(SymmetricKeysResource.class);
	
	@Autowired
	private SymmetricKeyRepository symmetricKeyRepository;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/keys", method = RequestMethod.GET)
	public ResponseEntity<List<SymmetricKey>> retrieveAll(@AuthenticationPrincipal CustomUser user) {
		
		logger.info(String.format("User logged : %s", user.getUsuario().getEmail()));
		
		List<SymmetricKey> symKeys = new ArrayList<SymmetricKey>();
		
		symKeys = symmetricKeyRepository.findAllByUsuario(user.getUsuario());
        if (symKeys.isEmpty()) {
        		logger.warn("Sin claves simétricas!");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/keys/{f}", method = RequestMethod.GET)
    public ResponseEntity<?> getKey(@AuthenticationPrincipal CustomUser user, @PathVariable("f") String f) {
		
		logger.info(String.format("User logged : %s", user.getUsuario().getEmail()));
		
        logger.info("Recuperando clave con id [{}] del usuario [{}]", f, user.getUsuario());
        Optional<SymmetricKey> existingSymKey = symmetricKeyRepository.findByFByUsuario(f, user.getUsuario());
        if (!existingSymKey.isPresent()) {
            logger.error("Clave con f [{}] del usuario [{}] NO encontrada.", f, user.getUsuario().getEmail());
            return new ResponseEntity(new CustomErrorType("Clave con f " + f  + " del usuario "+ user.getUsuario().getEmail() +" NO encontrada."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SymmetricKey>(existingSymKey.get(), HttpStatus.OK);
    }

}
