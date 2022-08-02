package tugasakhir.pemesanan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tugasakhir.pemesanan.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

    @Query(value = "select * from product where product_name LIKE %:cariParam%", nativeQuery = true)
    public List<Product> search(@Param("cariParam") String cari);
}
