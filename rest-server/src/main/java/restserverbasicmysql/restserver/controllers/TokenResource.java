package restserverbasicmysql.restserver.controllers;

import java.util.Calendar;
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

import restserverbasicmysql.restserver.model.Token;
import restserverbasicmysql.restserver.model.Usuario;
import restserverbasicmysql.restserver.repos.TokenRepository;
import restserverbasicmysql.restserver.repos.UsuarioRepository;

@RestController
public class TokenResource {
	
	public static final Logger logger = LoggerFactory.getLogger(TokenResource.class);
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<?> token(@PathVariable("token") String token) {
		
		logger.info("Received [{}]", token);
		
		Optional<Token> optionalToken = tokenRepository.findBytoken(token);
		if(optionalToken.isPresent()) {
			Token theToken = optionalToken.get();
			
			// Check if is valid temporarily
			Calendar cal = Calendar.getInstance();
			
			if(theToken.getValid_from().before(cal.getTime()) 
					&& theToken.getValid_to().after(cal.getTime())
					&& !theToken.isUsed()
					){
				logger.info("Time frame correct and token not used!");
				
				Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(theToken.getUsuario().getEmail());
				if(optionalUsuario.isPresent()) {
					Usuario theUsuario = optionalUsuario.get();
					theUsuario.setEnabled(true);
					Usuario theUsuarioUpdated = usuarioRepository.saveAndFlush(theUsuario);
					logger.info("theUsuarioUpdated: [{}]", theUsuarioUpdated.toString());
					
					theToken.setUsed(true);
					Token theTokenUpdated = tokenRepository.saveAndFlush(theToken);
					logger.info("theTokenUpdated: [{}]", theTokenUpdated.toString());
					
					return new ResponseEntity<String>("User " + theToken.getUsuario().getEmail() + " ENABLED.", HttpStatus.OK);
				}else {
					return new ResponseEntity<String>("User " + theToken.getUsuario().getEmail() + " NOT exists!.", HttpStatus.CONFLICT);
				}
				
				
			}else {
				logger.error("The token is NOT valid.");
				return new ResponseEntity<String>("The token is OUT of date and therfore NOT vàlid. User not enabled.", HttpStatus.CONFLICT);
			}
		}else {
			logger.error("The token is NOT valid.");
			return new ResponseEntity<String>("The token is NOT valid.", HttpStatus.CONFLICT);
		}
        
    }

}
