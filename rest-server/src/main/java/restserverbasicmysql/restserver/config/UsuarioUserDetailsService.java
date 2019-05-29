package restserverbasicmysql.restserver.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.CustomUser;
import restserverbasicmysql.restserver.model.Role;
import restserverbasicmysql.restserver.model.Usuario;
import restserverbasicmysql.restserver.repos.UsuarioRepository;

@Repository
public class UsuarioUserDetailsService implements UserDetailsService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		logger.info(String.format("Buscando el usuario [%s]...", username));
		
		Optional<Usuario> optUsuario = usuarioRepository.findByUsername(username);
		logger.info(String.format("Buscando el usuario [%s]...", optUsuario.isPresent()));
		if(!optUsuario.isPresent()){
			logger.error(String.format("El usuario [%s] NO se encuentra!", username));
			throw new UsernameNotFoundException(String.format("El usuario [%s] NO se encuentra!", username));
		}
		logger.info(String.format("El usuario está en el sistema [%s]...", optUsuario.isPresent()));
		Usuario usuario = optUsuario.get();
		logger.info(String.format("Usuario.email. [%s]...", usuario.getEmail()));
		//logger.info(String.format("Localizado el usuario [%s]...", usuario.toString()));
		
		try {
			logger.info(String.format("Localizado el usuario [%s][%d]...", usuario.getEmail(), usuario.getRoles().size()));
			for(Role role : usuario.getRoles()) {
				logger.info(String.format("Rol del usuario [%s]... [%s]", usuario.getEmail(), role.getNom()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean enabled = usuario.isEnabled();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		User user = new User(
				usuario.getUsername(), 
				usuario.getPassword(),
				enabled,
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				getAuthorities(usuario.getRoles()));
		
		logger.info(String.format("User: [%s]...", user.toString()));
		
		return new CustomUser(usuario, user);
		
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getNom()));
		}
		return authorities;
	}
}
