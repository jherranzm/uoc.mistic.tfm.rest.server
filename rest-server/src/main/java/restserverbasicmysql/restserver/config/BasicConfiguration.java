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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import restserverbasicmysql.restserver.model.Role;
import restserverbasicmysql.restserver.model.Usuario;
import restserverbasicmysql.restserver.repos.RoleRepository;
import restserverbasicmysql.restserver.repos.UsuarioRepository;

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
	public UserDetailsService userDetailsService() {
		logger.info(String.format("*** Init UserDetailsService!", ""));
		//PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails user = User.withUsername("user").password(passwordEncoder().encode("user")).roles("USER").build();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(user);
		return manager;

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
//		.and()
//		.inMemoryAuthentication()
//		.withUser("admin").password(passwordEncoder().encode("admin"))
//		.roles("ADMIN", "USER")
//		.and()
//		.withUser("user").password(passwordEncoder().encode("user"))
//		.roles("USER")
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
                //.antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/token").permitAll()
                
                .antMatchers(HttpMethod.POST, "/login").hasRole("USER") // I/PostDataToUrlTask: Código de respuesta del servidor : [200]
                //.antMatchers(HttpMethod.POST, "/login").hasRole("ADMIN") // I/PostDataToUrlTask: Código de respuesta del servidor : [403]
                
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