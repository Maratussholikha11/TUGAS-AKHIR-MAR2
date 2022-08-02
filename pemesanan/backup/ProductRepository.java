package tugasakhir.pemesanan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tugasakhir.pemesanan.model.Ordering;
import tugasakhir.pemesanan.model.Product;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select o from Product o where o.id_product = :id_product")
    public Product findProductById(@PathParam("id_product") Integer id_product);

    @Query("select o from Product o where o.productName = :productName")
    public Product findProductByName(@PathParam("productName") String productName);

  //  @Query("select p from Product p where p.name like :name")
    public List<Product> findProductByProductNameLike(String name);


    List<Product> findByProductNameContains(String name);







}
