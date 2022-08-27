package tugasakhir.pemesanan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tugasakhir.pemesanan.model.Ordering;
import tugasakhir.pemesanan.model.Sale;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    @Query("select s from Sale s where s.transaction.id_transaction = :id")
    public Sale findSaleByTrxId(@PathParam("id") Integer id);
}
