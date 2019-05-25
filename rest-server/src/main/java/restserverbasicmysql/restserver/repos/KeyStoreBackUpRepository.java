package restserverbasicmysql.restserver.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.KeyStoreBackUp;
import restserverbasicmysql.restserver.model.Usuario;

@Repository
public interface KeyStoreBackUpRepository extends JpaRepository<KeyStoreBackUp, Long> {
	Optional<KeyStoreBackUp> findByUsuario(Usuario usuario);
}
