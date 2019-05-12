package restserverbasicmysql.restserver.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	Optional<Role> findByNom(String nombre);
}
