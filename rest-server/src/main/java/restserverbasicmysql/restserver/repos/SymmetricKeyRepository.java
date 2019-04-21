package restserverbasicmysql.restserver.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.SymmetricKey;

@Repository
public interface SymmetricKeyRepository extends JpaRepository<SymmetricKey, String> {
	Optional<SymmetricKey> findByF(String f);
}
