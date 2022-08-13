package tugasakhir.pemesanan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tugasakhir.pemesanan.dto.SearchData;
import tugasakhir.pemesanan.model.Product;
import tugasakhir.pemesanan.repository.ProductRepository;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public void saveproduct(Product product) {
		productRepository.save(product);
	}

	@Override
	public List<Product> getAllActiveproducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getproductById(Integer id) {
		return productRepository.getById(id);
	}

	@Override
	public Product findById(Integer id){
		return productRepository.getById(id);
	}

	@Override
	public List<Product> getproduct(SearchData searchData) {
		return productRepository.search(searchData.getSearchKey());
	}

	@Override
	public Product update(Product product) {
		Product model = new Product();
		Product result = new Product();
		try {
			model.setProductName(product.getProductName());
			model.setFileName(product.getFileName());
			model.setDescription(product.getDescription());
			model.setImage(product.getImage());
			model.setPrice(product.getPrice());
			result = productRepository.save(model);
		}catch (Exception e){

		}
		return result;
	}


	@Override
	public boolean delete(Integer kode) {
		Product model = productRepository.getById(kode);
		productRepository.delete(model);
		return true;
	}

}
