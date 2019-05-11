package restserverbasicmysql.restserver.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import restserverbasicmysql.restserver.model.Usuario;
import restserverbasicmysql.restserver.repos.UsuarioRepository;
import restserverbasicmysql.restserver.vo.UserPasswordObject;

@RestController
public class SignUpResource {
	
	public static final Logger logger = LoggerFactory.getLogger(SignUpResource.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody UserPasswordObject userPasswordObject) {
		
        logger.info("Received [{}]", userPasswordObject.toString());
        
        // Does the username exists in the users table
        Optional<Usuario> existingUsuario = usuarioRepository.findByUsername(userPasswordObject.getUsername());
        
        if(!existingUsuario.isPresent()) {
        	// User does NOT exists
        	Usuario usuario = new Usuario();
        	usuario.setUsername(userPasswordObject.getUsername());
        	usuario.setPass(passwordEncoder.encode(userPasswordObject.getPass()));
        	usuario.setEmail(userPasswordObject.getUsername());
        	usuario.setEnabled(false);
        	
        	Usuario registeredUsuario = usuarioRepository.save(usuario);
        	
        	logger.info("Registered :  [{}]", registeredUsuario.toString());
        	
        }else {
        	// ERROR : user already in table... :-(
        	logger.info("ERROR : user already in table :  [{}]", existingUsuario.get().toString());
        }
        
        
        return new ResponseEntity<String>("OK!", HttpStatus.OK);
    }

}
