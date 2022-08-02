package tugasakhir.pemesanan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tugasakhir.pemesanan.model.Ordering;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Integer> {

    @Query("select o from Ordering o where o.user.idUser = :idUser")
    public List<Ordering> findOrderingByUser(@PathParam("idUser") Integer idUser);

    @Query("SELECT o FROM Ordering o  INNER JOIN User u ON o.user.idUser = u.idUser WHERE o.user.username = :username")
    List<Ordering> findOrderingByUsername(@Param("username") String username);

    List<Ordering> findByPercentageBetween(Integer a, Integer b);

}