package restserverbasicmysql.restserver.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.Invoice;
import restserverbasicmysql.restserver.model.Usuario;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
	
	Optional<Invoice> findByUid(String uid);
	
	Optional<Invoice> findById(Long id);
	
	@Query("SELECT i FROM Invoice i WHERE i.usuario = ?1")
	List<Invoice> findAllByUsuario(Usuario usuario);

	@Query("SELECT i FROM Invoice i WHERE i.id = ?1 and i.usuario = ?2")
	Optional<Invoice> findByIdByUsuario(long id, Usuario usuario);
}
