package restserverbasicmysql.restserver.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
	Optional<Invoice> findByUid(String uid);
}
