package restserverbasicmysql.restserver.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	Optional<Usuario> findById(Long uid);
	Optional<Usuario> findByUsername(String username);
}
