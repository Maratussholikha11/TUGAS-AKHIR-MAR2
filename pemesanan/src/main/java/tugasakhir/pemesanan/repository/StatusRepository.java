package tugasakhir.pemesanan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tugasakhir.pemesanan.model.Ordering;
import tugasakhir.pemesanan.model.Status;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    @Query("select o from Status o where o.name = :name")
    public Status findStatusByName(@PathParam("name") String name);
}
