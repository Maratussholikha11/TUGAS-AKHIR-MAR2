package tugasakhir.pemesanan.service;

import tugasakhir.pemesanan.dto.SearchData;
import tugasakhir.pemesanan.model.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService {
	void saveProduct(Product product);
	List<Product> getAllActiveProducts();
	Product getProductById(Integer id);
	public Product findById(Integer id);
	public List<Product> getProduct(SearchData searchData);
	public Product update(Product product);
	public boolean delete(Integer id);

}
