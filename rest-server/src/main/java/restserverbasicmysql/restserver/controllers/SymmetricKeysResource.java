package restserverbasicmysql.restserver.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import restserverbasicmysql.restserver.model.SymmetricKey;
import restserverbasicmysql.restserver.model.UploadKeyObject;
import restserverbasicmysql.restserver.repos.SymmetricKeyRepository;

@RestController
public class SymmetricKeysResource {
	
	public static final Logger logger = LoggerFactory.getLogger(SymmetricKeysResource.class);
	
	
	@Autowired
	private SymmetricKeyRepository symmetricKeyRepository;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/keys", method = RequestMethod.GET)
	public ResponseEntity<List<SymmetricKey>> retrieveAll() {
		List<SymmetricKey> symKeys = symmetricKeyRepository.findAll();
        if (symKeys.isEmpty()) {
        		logger.warn("Sin claves simétricas!");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Se han recuperado [{}] claves simétricas", symKeys.size());
        return new ResponseEntity<List<SymmetricKey>>(symKeys, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/keys", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody UploadKeyObject uploadObject) {

		logger.info("Objeto recibido:  [{}]", uploadObject);
        SymmetricKey symKey = getKeysFromUploadObject(uploadObject);
        
        logger.info("Guardando la clave  [{}]", symKey);
        SymmetricKey newSymKey= new SymmetricKey();
        Optional<SymmetricKey> existingSymKey = symmetricKeyRepository.findByF(symKey.getF());

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

}
