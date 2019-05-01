package restserverbasicmysql.restserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import restserverbasicmysql.restserver.config.CreateCSR;
import restserverbasicmysql.restserver.vo.UploadCSR;

@RestController
public class CertificateResource {
	
	public static final Logger logger = LoggerFactory.getLogger(CertificateResource.class);
	
	@RequestMapping(value = "/cert/csr", method = RequestMethod.POST)
    public ResponseEntity<?> postCSR(@RequestBody UploadCSR uploadCSR) {
		
        logger.info("CSR [{}]", uploadCSR.toString());
        return new ResponseEntity<String>(CreateCSR.getCertificateFromCSR(uploadCSR.getCsr()), HttpStatus.OK);
    }

}
