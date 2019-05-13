package restserverbasicmysql.restserver.config;

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

@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UsuarioUserDetailsService uuds;
	
	
	@Bean
	public UserDetailsService userDetailsService() {

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
		auth
		.userDetailsService(uuds)
		.passwordEncoder(passwordEncoder())
		.and()
		.inMemoryAuthentication()
		.withUser("admin").password(passwordEncoder().encode("admin"))
		.roles("ADMIN", "USER")
		.and()
		.withUser("user").password(passwordEncoder().encode("user"))
		.roles("USER")
		;
	}
	
	// Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

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
                .antMatchers(HttpMethod.PUT, "/facturas/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/facturas/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/facturas/**").hasRole("ADMIN")
                
                .antMatchers(HttpMethod.GET, "/keys/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/keys").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/keys/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/keys/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/keys/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

}