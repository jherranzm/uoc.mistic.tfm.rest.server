package restserverbasicmysql.restserver.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.BackUp;

@Repository
public interface BackUpRepository extends JpaRepository<BackUp, String> {
	Optional<BackUp> findByF1(String f1);
}
