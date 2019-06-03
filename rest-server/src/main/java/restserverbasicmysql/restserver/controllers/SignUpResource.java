package restserverbasicmysql.restserver.controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
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

import restserverbasicmysql.restserver.mail.MailService;
import restserverbasicmysql.restserver.model.Role;
import restserverbasicmysql.restserver.model.Token;
import restserverbasicmysql.restserver.model.Usuario;
import restserverbasicmysql.restserver.repos.RoleRepository;
import restserverbasicmysql.restserver.repos.TokenRepository;
import restserverbasicmysql.restserver.repos.UsuarioRepository;
import restserverbasicmysql.restserver.util.TokenGenerator;
import restserverbasicmysql.restserver.vo.UserPasswordObject;

@RestController
public class SignUpResource {

	public static final Logger logger = LoggerFactory.getLogger(SignUpResource.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> signup(@RequestBody UserPasswordObject userPasswordObject) {

		logger.info("Received [{}]", userPasswordObject.toString());
		
		if(userPasswordObject.getOp() == null 
				|| userPasswordObject.getOp().isEmpty() 
				|| !userPasswordObject.getOp().equals("signup")
				){
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("responseCode", HttpStatus.BAD_REQUEST.value());
			json.put("message", "Operation NOT informed or NOT supported");
			logger.info("Message returned [{}]", json);
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		
		if(userPasswordObject.getUsername() == null 
				|| userPasswordObject.getUsername().isEmpty() 
				|| !isValidEmail(userPasswordObject.getUsername())
				){
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("responseCode", HttpStatus.BAD_REQUEST.value());
			json.put("message", "User is NOT a valid email");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}
		
		if(userPasswordObject.getPass() == null 
				|| userPasswordObject.getPass().isEmpty() 
				|| !isValidPass(userPasswordObject.getPass())
				){
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("responseCode", HttpStatus.BAD_REQUEST.value());
			json.put("message", "Password NOT enough long");
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.BAD_REQUEST);
		}

		// Does the username exists in the users table
		Optional<Usuario> existingUsuario = usuarioRepository.findByUsername(userPasswordObject.getUsername());

		if (!existingUsuario.isPresent()) {
			// User does NOT exists
			Usuario usuario = new Usuario();
			usuario.setUsername(userPasswordObject.getUsername());
			usuario.setPass(passwordEncoder.encode(userPasswordObject.getPass()));
			usuario.setEmail(userPasswordObject.getUsername());
			usuario.setEnabled(false);

			Set<Role> roles = new HashSet<Role>();
			Role role = roleRepository.findByNom("ROLE_USER").get();
			roles.add(role);
			usuario.setRoles(roles);

			Usuario registeredUsuario = usuarioRepository.saveAndFlush(usuario);


			logger.info("Registered :  [{}]", registeredUsuario.toString());

			Map<String, Object> json = new HashMap<String, Object>();
			json.put("responseCode", HttpStatus.OK.value());
			json.put("message", String.format("Registered :  [%s]", registeredUsuario.getEmail()));
			json.put("usuario", usuario);

			String token = TokenGenerator.nextToken();
			logger.info("Super token : [{}]", token);

			Token theToken = new Token();
		
			theToken.setToken(token);
			theToken.setUsuario(registeredUsuario);
			Calendar cal = Calendar.getInstance();
			theToken.setValid_from(cal.getTime());
			
			theToken.setResum(DigestUtils.md5Hex(token).toUpperCase());
			
			cal.add(Calendar.MINUTE, 60);
			theToken.setValid_to(cal.getTime());
			
			logger.info("Super token : [{}]", theToken);
			
			tokenRepository.save(theToken);
			
			
			mailService.sendMail(registeredUsuario.getEmail(), "Registered!!!! https://localhost:8443/token/" + token);
			
			
			

			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.OK);

		} else {
			// ERROR : user already in table... :-(
			logger.info("ERROR : user already in table :  [{}]", existingUsuario.get().toString());

			Map<String, Object> json = new HashMap<String, Object>();
			json.put("responseCode", HttpStatus.CONFLICT.value());
			json.put("message", String.format("ERROR : user [%s] ALREADY in table!", existingUsuario.get().getEmail()));
			return new ResponseEntity<Map<String, Object>>(json, HttpStatus.CONFLICT);
		}

		// return new ResponseEntity<String>("OK!", HttpStatus.OK);
	}
	
	private boolean isValidPass(String pass) {
		
		return (pass.length() >= 12);
	}

	private boolean isValidEmail(String email) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		 
		    Matcher matcher = pattern.matcher(email);
		    logger.info(email +" : "+ matcher.matches());
		return matcher.matches();
	}

}

