package tugasakhir.pemesanan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tugasakhir.pemesanan.model.Ordering;
import tugasakhir.pemesanan.model.product;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface productRepository extends JpaRepository<product, Integer> {

    @Query("select o from product o where o.id_product = :id_product")
    public product findproductById(@PathParam("id_product") Integer id_product);

    @Query("select o from product o where o.productName = :productName")
    public product findproductByName(@PathParam("productName") String productName);

  //  @Query("select p from product p where p.name like :name")
    public List<product> findproductByproductNameLike(String name);


    List<product> findByproductNameContains(String name);







}
