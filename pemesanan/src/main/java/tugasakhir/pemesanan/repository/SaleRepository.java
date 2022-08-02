package tugasakhir.pemesanan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tugasakhir.pemesanan.model.Sale;
@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
