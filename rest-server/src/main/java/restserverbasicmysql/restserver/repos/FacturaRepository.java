package restserverbasicmysql.restserver.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restserverbasicmysql.restserver.model.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
	Optional<Factura> findByNumFactura(String numFactura);
}
