package tugasakhir.pemesanan.service;

import tugasakhir.pemesanan.dto.SearchData;
import tugasakhir.pemesanan.model.Product;

import java.util.List;


public interface ProductService {
	void saveproduct(Product product);
	List<Product> getAllActiveproducts();
	Product getproductById(Integer id);
	public Product findById(Integer id);
	public List<Product> getproduct(SearchData searchData);
	public Product update(Product product);
	public boolean delete(Integer id);

}
