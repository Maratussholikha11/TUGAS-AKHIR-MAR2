package tugasakhir.pemesanan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tugasakhir.pemesanan.model.Ordering;
import tugasakhir.pemesanan.model.Transaction;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("select o from Transaction o where o.user.idUser = :idUser")
    public List<Transaction> findOrderingByUser(@PathParam("idUser") Integer idUser);

    @Query("select o from Transaction o where o.user.idUser = :idUser")
    public List<Transaction> findOrderingByOrder(@PathParam("idUser") Integer idUser);
}
