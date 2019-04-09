package restserverbasicmysql.restserver.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.InvoiceData;

@Repository
public interface InvoiceDataRepository extends JpaRepository<InvoiceData, String> {
	Optional<InvoiceData> findByF1(String f1);

}
