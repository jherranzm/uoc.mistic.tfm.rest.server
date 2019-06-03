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
	
	private static final String USER_ENABLED = "User %s ENABLED!.";
	private static final String USER_DOES_NOT_EXIST = "User %s DOES NOT exist!.";
	private static final String THE_TOKEN_IS_OUT_OF_DATE = "The token is OUT of date and therefore NOT valid. User not enabled.";
	private static final String THE_TOKEN_IS_NOT_VALID = "The token is NOT valid.";

	public static final Logger logger = LoggerFactory.getLogger(TokenResource.class);
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<?> token(@PathVariable("token") String token) {
		
		//TODO: Les respostes han d'esser en format texte NO en format JSON
		logger.info("Received [{}]", token);
		
		Optional<Token> optionalToken = tokenRepository.findBytoken(token);
		if(!optionalToken.isPresent()) {
			logger.error(THE_TOKEN_IS_NOT_VALID);
			return new ResponseEntity<String>(THE_TOKEN_IS_NOT_VALID, HttpStatus.OK);
		}
		Token theToken = optionalToken.get();
		
		// Check if is valid temporarily
		Calendar cal = Calendar.getInstance();
		
		if(!theToken.getValid_from().before(cal.getTime()) 
				|| !theToken.getValid_to().after(cal.getTime())
				|| theToken.isUsed()
				){
			logger.error(THE_TOKEN_IS_OUT_OF_DATE);
			return new ResponseEntity<String>(THE_TOKEN_IS_OUT_OF_DATE, HttpStatus.OK);
		}
		logger.info("Time frame correct and token not used!");
			
		Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(theToken.getUsuario().getEmail());
		if(!optionalUsuario.isPresent()) {
			String message = String.format(USER_DOES_NOT_EXIST, theToken.getUsuario().getEmail());
			logger.info(message);
			return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
		}
		
		Usuario theUsuario = optionalUsuario.get();
		theUsuario.setEnabled(true);
		Usuario theUsuarioUpdated = usuarioRepository.saveAndFlush(theUsuario);
		logger.info("theUsuarioUpdated: [{}]", theUsuarioUpdated.toString());
		
		theToken.setUsed(true);
		Token theTokenUpdated = tokenRepository.saveAndFlush(theToken);
		logger.info("theTokenUpdated: [{}]", theTokenUpdated.toString());
		String message = String.format(USER_ENABLED, theToken.getUsuario().getEmail());
		logger.info(message);
		return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
    }

}
