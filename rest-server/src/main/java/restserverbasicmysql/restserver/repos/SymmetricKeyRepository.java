package restserverbasicmysql.restserver.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import restserverbasicmysql.restserver.model.SymmetricKey;
import restserverbasicmysql.restserver.model.Usuario;

@Repository
public interface SymmetricKeyRepository extends JpaRepository<SymmetricKey, String> {
	Optional<SymmetricKey> findByF(String f);

	@Query("SELECT i FROM SymmetricKey i WHERE i.usuario = ?1")
	List<SymmetricKey> findAllByUsuario(Usuario usuario);

	@Query("SELECT i FROM SymmetricKey i WHERE i.f = ?1 and i.usuario = ?2")
	Optional<SymmetricKey> findByFByUsuario(String f, Usuario usuario);

	@Query("SELECT i FROM SymmetricKey i WHERE i.usuario = ?1")
	List<SymmetricKey> findByByUsuario(Usuario usuario);

	@Transactional
	@Modifying
	@Query("DELETE FROM SymmetricKey i WHERE i.usuario = ?1")
	void deleteAllByUser(Usuario usuario);
}
