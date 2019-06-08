package restserverbasicmysql.restserver.config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import restserverbasicmysql.restserver.model.Role;
import restserverbasicmysql.restserver.model.Usuario;
import restserverbasicmysql.restserver.repos.RoleRepository;
import restserverbasicmysql.restserver.repos.UsuarioRepository;
import restserverbasicmysql.restserver.util.UsuarioUserDetailsService;

@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {

	private static final String USUARIO_APP = "UsuarioApp";

	public static final Logger logger = LoggerFactory.getLogger(BasicConfiguration.class);
	
	@Autowired
	private UsuarioUserDetailsService uuds;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public void createDefaultUser() {
		
		Optional<Usuario> existingUsuario = usuarioRepository.findByUsername(USUARIO_APP);

		if (!existingUsuario.isPresent()) {
			// User does NOT exists
			Usuario usuario = new Usuario();
			usuario.setUsername(USUARIO_APP);
			usuario.setPass(passwordEncoder.encode(USUARIO_APP));
			usuario.setEmail(USUARIO_APP);
			usuario.setEnabled(true);

			Set<Role> roles = new HashSet<Role>();
			Role role = roleRepository.findByNom("ROLE_USER").get();
			roles.add(role);
			usuario.setRoles(roles);

			Usuario registeredUsuario = usuarioRepository.saveAndFlush(usuario);
			logger.info(String.format("*** createDefaultUser : %s", registeredUsuario.toString()));
		}

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		
		logger.info(String.format("*** Init configureGlobal!", ""));
		
		auth
		.userDetailsService(uuds)
		.passwordEncoder(passwordEncoder())
		;
	}
	
	// Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	logger.info(String.format("*** Init configure!", ""));
    	
        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                
                .authorizeRequests()
                
                .antMatchers("/status").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/token").permitAll()
                .antMatchers("/ret/token").permitAll()
                
                .antMatchers(HttpMethod.POST, "/login").hasRole("USER") 
                
                .antMatchers(HttpMethod.GET, "/facturas/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/facturas").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/facturas/**").hasRole("ADMIN")
                
                .antMatchers(HttpMethod.GET, "/keys/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/keys").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/keys/**").hasRole("USER")
                
                .antMatchers(HttpMethod.GET, "/ksb/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/ksb").hasRole("USER")

                .and()
                .csrf().disable()
                .formLogin().disable();
    }

}