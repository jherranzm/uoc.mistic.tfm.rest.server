package restserverbasicmysql.restserver.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findById(Long uid);
}
